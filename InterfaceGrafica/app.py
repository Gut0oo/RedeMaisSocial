import tkinter as tk
from tkinter import messagebox, ttk
import requests
import json
import sys
# import time # Removido, pois a chamada não é mais simulada

# --------------------- URLs da API ---------------------
# URL para verificar se o CPF/Email já existe (Tela 1)
URL_VERIFICAR = "http://localhost:8080/cadastro/verificar-usuario" 
# URL para finalizar o cadastro de Pessoa Física (Tela Senha)
URL_AFILIAR = "http://localhost:8080/cadastro/afiliar-pessoa-fisica" 

URL_API_APROVACAO_TERMO = "http://localhost:8080/afiliacoes/1/aprovacaoTermo"
URL_API_VERIFICACAO_CODIGO = "http://localhost:8080/afiliacoes/verificarCodigo"
# Dicionário para armazenar todos os dados coletados ao longo das telas
dados_cadastro = {}

class AplicacaoCadastro:
    def __init__(self, master):
        self.master = master
        self.master.title("Rede Mais Social - Cadastro")
        self.master.geometry("500x750")
        
        # Cor de fundo principal
        self.master.configure(bg="#4CAF50") 
        
        # Inicializa a primeira tela
        self.mostrar_tela1()

    def limpar_tela(self):
        # Remove todos os widgets da tela atual
        for widget in self.master.winfo_children():
            widget.destroy()

    def mostrar_tela1(self):
        self.limpar_tela()
        # Tela 1 agora recebe a função de AVANÇAR APÓS VERIFICAÇÃO
        Tela1(self.master, self.proximo_passo)

    def mostrar_tela2(self):
        self.limpar_tela()
        Tela2(self.master, self.proximo_passo, self.voltar_passo)

    def mostrar_telaSenha(self):
        self.limpar_tela()
        TelaSenha(self.master, self.mostrar_tela_termo, self.voltar_passo)

    def mostrar_tela3(self):
        self.limpar_tela()
        # Tela 3 avança para Tela Senha
        Tela3(self.master, self.proximo_passo, self.voltar_passo)
        
    def mostrar_tela_termo(self):
        self.limpar_tela()
        TelaTermo(self.master, self.mostrar_tela_validar_codigo, self.voltar_passo)

    def mostrar_tela_validar_codigo(self):
        self.limpar_tela()
        TelaValidarCodigo(self.master, lambda: self.finalizar_cadastro({}), self.mostrar_tela_termo)
    
    def proximo_passo(self, dados):
        # Atualiza o dicionário global de dados e avança para a próxima tela
        global dados_cadastro
        dados_cadastro.update(dados)
        
        # 1. TELA 1 (Verificação) -> TELA 2 (Dados Pessoais + Endereço)
        if "email" in dados_cadastro and "cpf" in dados_cadastro and "nome" not in dados_cadastro: 
             self.mostrar_tela2()
        
        # 2. TELA 2 (Dados Pessoais + Endereço) -> TELA 3 (Informações Adicionais + Formação)
        # 'nome' está preenchido, mas 'sobreMim' (da Tela 3) ainda não.
        elif "nome" in dados_cadastro and "sobreMim" not in dados_cadastro:
             self.mostrar_tela3()
             
        # 3. TELA 3 (Informações Adicionais + Formação) -> TELA SENHA
        # 'sobreMim' está preenchido, mas 'senha' (da Tela Senha) ainda não.
        elif "sobreMim" in dados_cadastro and "senha" not in dados_cadastro:
             self.mostrar_telaSenha()
             
        else:
            # Caso inesperado, volta para a Tela 1
            self.mostrar_tela1()

    def voltar_passo(self):
        # Lógica de retorno para a tela anterior
        global dados_cadastro
        
        # 1. Se estiver na Tela Senha (já tem senha), volta para a Tela 3
        if "senha" in dados_cadastro:
            if "senha" in dados_cadastro: del dados_cadastro["senha"]
            self.mostrar_tela3()
            
        # 2. Se estiver na Tela 3 (já tem sobreMim), volta para a Tela 2
        elif "sobreMim" in dados_cadastro: 
            # Limpar campos da Tela 3 e Formação
            campos_tela3 = ["sobreMim", "areaAtuacao", "apoiaOutrasCausas", "Habilidades", 
                            "curso", "nivelFormacao", "instituicao", "dataInicio", "dataConclusao"] 
            for campo in campos_tela3:
                if campo in dados_cadastro:
                    del dados_cadastro[campo]
            self.mostrar_tela2()
            
        # 3. Se estiver na Tela 2 (já tem nome), volta para a Tela 1
        elif "nome" in dados_cadastro:
            # Limpar campos da Tela 2 e Endereço
            campos_tela2 = ["nome", "telefone", "sexo", "dataNascimento", "endereco", "profissao", "pais", 
                            "cep", "estado", "cidade"] 
            for campo in campos_tela2:
                if campo in dados_cadastro:
                    del dados_cadastro[campo]
            self.mostrar_tela1()
            
        else:
            messagebox.showinfo("Fim", "Você está na primeira tela. Clique em 'Iniciar Cadastro' ou feche a janela.")
            
    def finalizar_cadastro(self, dados_finais):
        # Última atualização de dados (senha) e chamada à API de Afiliação
        global dados_cadastro
        dados_cadastro.update(dados_finais)

        # O campo 'idade' será mockado, pois não há função de cálculo de idade neste código.
        if "dataNascimento" in dados_cadastro:
             dados_cadastro["idade"] = 32 

        # Remove chaves com None ou vazias 
        dados_finais_limpos = {k: v for k, v in dados_cadastro.items() if v is not None and v != ""}

        messagebox.showinfo("Dados Finais", f"Dados prontos para envio para {URL_AFILIAR}:\n{json.dumps(dados_finais_limpos, indent=2)}")

        try:
            # CHAMADA À API DE AFILIAÇÃO (ATIVA)
            # ATENÇÃO: A UI PODE CONGELAR durante esta chamada, pois ela é síncrona.
            response = requests.post(URL_AFILIAR, json=dados_finais_limpos)
            
            if response.status_code in [200, 201]:
                messagebox.showinfo("Sucesso", "Cadastro enviado com sucesso!")
                
                # Retorna para o início após o sucesso
                dados_cadastro.clear()
                self.mostrar_tela1()
            else:
                messagebox.showerror("Erro", f"Erro {response.status_code}: {response.text}")

        except Exception as e:
            messagebox.showerror("Erro de Conexão", f"Não foi possível conectar à API de Afiliação: {str(e)}")

class Tela1(tk.Frame):
    def __init__(self, master, proximo_callback):
        super().__init__(master, bg=master.cget('bg')) # Herda o fundo
        self.master = master
        self.proximo_callback = proximo_callback
        self.pack(expand=True, fill="both", padx=30, pady=50)

        # --------------------- Título e Logo (Simulados) ----------------------
        # Criando um frame para centralizar o conteúdo
        self.main_frame = tk.Frame(self, bg=master.cget('bg'))
        self.main_frame.pack(expand=True)
        
        # Logo (Simulação de um espaço para logo)
        tk.Label(self.main_frame, text="Rede Mais Social", font=("Helvetica", 24, "bold"), bg=master.cget('bg'), fg="white").pack(pady=(0, 5))
        tk.Label(self.main_frame, text="Conectando pessoas, transformando vidas.", font=("Helvetica", 12), bg=master.cget('bg'), fg="lightgray").pack(pady=(0, 20))
        tk.Label(self.main_frame, text="Verifique seu CPF/CNPJ e Email", font=("Helvetica", 10), bg=master.cget('bg'), fg="lightgray").pack(pady=(0, 30))

        # --------------------- Campos ----------------------
        
        # Estilização de Labels e Entries para melhor visual
        style = ttk.Style()
        style.configure("TLabel", background=master.cget('bg'), foreground="white", font=("Helvetica", 10))
        style.configure("TEntry", fieldbackground="white", foreground="black", font=("Helvetica", 12))

        # Email
        ttk.Label(self.main_frame, text="Email:").pack(anchor='w', pady=(10, 0))
        self.email_entry = ttk.Entry(self.main_frame, width=40, style="TEntry")
        self.email_entry.pack(fill='x', ipady=5)

        # CPF/CNPJ
        ttk.Label(self.main_frame, text="CPF / CNPJ:").pack(anchor='w', pady=(15, 0))
        self.cpf_entry = ttk.Entry(self.main_frame, width=40, style="TEntry")
        self.cpf_entry.pack(fill='x', ipady=5)

        # --------------------- Botões ----------------------

        # Botão Iniciar Cadastro
        self.btn_iniciar = tk.Button(self.main_frame, text="Iniciar Cadastro", command=self.verificar_e_avancar, # Novo nome do método
                                     bg="white", fg="#4CAF50", font=("Helvetica", 14, "bold"),
                                     relief="groove", bd=2, padx=30, pady=10)
        self.btn_iniciar.pack(pady=40, fill='x')

        # Link Login
        tk.Label(self.main_frame, text="Já tem cadastro? Clique aqui para seguir para o Login", font=("Helvetica", 10), bg=master.cget('bg'), fg="lightgray").pack()


    def verificar_e_avancar(self):
        email = self.email_entry.get().strip()
        cpf = self.cpf_entry.get().strip()
        
        if not email or not cpf:
            messagebox.showwarning("Atenção", "Por favor, preencha todos os campos.")
            return

        dados_verificacao = {
            "email": email,
            "cpf": cpf,
        }
        
        try:
            # CHAMADA À API DE VERIFICAÇÃO (ATIVA)
            # ATENÇÃO: A UI PODE CONGELAR durante esta chamada, pois ela é síncrona.
            response = requests.post(URL_VERIFICAR, json=dados_verificacao)
            
            if response.status_code == 404:
                # Avança se a API retornar sucesso (usuário não cadastrado ou pronto para continuar)
                messagebox.showinfo("Verificação", "Usuário pronto para cadastro. Prosseguindo...")
                self.proximo_callback(dados_verificacao)
            else:
                # Exemplo: O usuário já existe ou dados são inválidos
                messagebox.showerror("Erro de Verificação", f"Erro {response.status_code}: {response.text}")
        
        except Exception as e:
            messagebox.showerror("Erro de Conexão", f"Não foi possível conectar à API de Verificação: {str(e)}")
            return


class Tela2(tk.Frame):
    def __init__(self, master, proximo_callback, voltar_callback):
        super().__init__(master, bg=master.cget('bg'))
        self.master = master
        self.proximo_callback = proximo_callback
        self.voltar_callback = voltar_callback
        self.pack(expand=True, fill="both", padx=30, pady=20)
        
        # Frame de rolagem, pois a tela tem muitos campos
        canvas = tk.Canvas(self, bg=master.cget('bg'), highlightthickness=0)
        scrollbar = ttk.Scrollbar(self, orient="vertical", command=canvas.yview)
        scrollable_frame = tk.Frame(canvas, bg=master.cget('bg'))

        scrollable_frame.bind(
            "<Configure>",
            lambda e: canvas.configure(
                scrollregion=canvas.bbox("all")
            )
        )

        canvas.create_window((0, 0), window=scrollable_frame, anchor="nw")
        canvas.configure(yscrollcommand=scrollbar.set)
        
        canvas.pack(side="left", fill="both", expand=True)
        scrollbar.pack(side="right", fill="y")
        
        # --------------------- Título ----------------------
        tk.Label(scrollable_frame, text="Insira seus Dados Pessoais e Endereço", font=("Helvetica", 20, "bold"), bg=master.cget('bg'), fg="white").pack(pady=(10, 20))
        
        # --------------------- Campos ----------------------
        self.entries = {}
        
        # Campos Pessoais
        campos_pessoais = [
            ("Nome Completo:", "nome"),
            ("Telefone / Whatsapp:", "telefone"),
            ("Sexo (Ex: Masculino, Feminino):", "sexo"),
            ("Data de Nascimento (AAAA-MM-DD):", "dataNascimento"),
            ("Profissão:", "profissao"),
        ]
        
        # Campos de Endereço
        campos_endereco = [
            ("CEP:", "cep"),         
            ("Estado (UF):", "estado"), 
            ("Cidade:", "cidade"),   
            ("Rua/Bairro/Número:", "endereco"), # Rua, Nº, Bairro, etc.
            ("País de Residência (Ex: Brasil):", "pais"), 
        ]
        
        todos_campos = campos_pessoais + campos_endereco
        
        for label_text, field_name in todos_campos:
            ttk.Label(scrollable_frame, text=label_text).pack(anchor='w', pady=(10, 0))
            entry = ttk.Entry(scrollable_frame, width=50, style="TEntry")
            entry.pack(fill='x', ipady=5)
            self.entries[field_name] = entry

        # --------------------- Botão Avançar ----------------------
        
        # Frame para o botão Avançar
        btn_frame = tk.Frame(scrollable_frame, bg=master.cget('bg'))
        btn_frame.pack(fill='x', pady=(30, 10))

        # Botão Retornar 
        btn_retornar = tk.Button(btn_frame, text="Retornar", command=self.voltar_callback,
                                     bg="#778899", fg="white", font=("Helvetica", 12),
                                     relief="groove", bd=2, padx=15, pady=5)
        btn_retornar.pack(side='left', padx=(0, 10))

        # Botão Avançar
        self.btn_avancar = tk.Button(btn_frame, text="Avançar", command=self.coletar_e_avancar,
                                     bg="#2E8B57", fg="white", font=("Helvetica", 14, "bold"),
                                     relief="flat", bd=0, padx=20, pady=8)
        self.btn_avancar.pack(side='right')

    def coletar_e_avancar(self):
        dados = {}
        todos_preenchidos = True
        
        for field, entry in self.entries.items():
            valor = entry.get().strip()
            if not valor:
                todos_preenchidos = False
            dados[field] = valor
        
        if not todos_preenchidos:
            messagebox.showwarning("Atenção", "Por favor, preencha todos os campos da Tela 2.")
            return

        self.proximo_callback(dados)

class TelaSenha(tk.Frame):
    def __init__(self, master, enviar_callback, voltar_callback):
        super().__init__(master, bg=master.cget('bg'))
        self.master = master
        self.enviar_callback = enviar_callback # Agora chama o método de envio da API
        self.voltar_callback = voltar_callback
        self.pack(expand=True, fill="both", padx=30, pady=50)

        self.show_password_var = tk.StringVar(value="0") # 0 = ocultar, 1 = mostrar
        
        # Criando um frame para centralizar o conteúdo
        self.main_frame = tk.Frame(self, bg=master.cget('bg'))
        self.main_frame.pack(expand=True)
        
        # --------------------- Título ----------------------
        tk.Label(self.main_frame, text="Criar Senha (Último Passo)", font=("Helvetica", 20, "bold"), bg=master.cget('bg'), fg="white").pack(pady=(10, 40))
        
        # --------------------- Senha ----------------------
        ttk.Label(self.main_frame, text="Senha:").pack(anchor='w', pady=(10, 0))
        self.senha_entry = ttk.Entry(self.main_frame, width=40, style="TEntry", show='*')
        self.senha_entry.pack(fill='x', ipady=5)
        
        # Checkbox Mostrar Senha
        tk.Checkbutton(self.main_frame, text="Mostrar Senha", variable=self.show_password_var, 
                       onvalue="1", offvalue="0", command=self.toggle_show_password, 
                       bg=master.cget('bg'), fg="white", selectcolor="#4CAF50").pack(anchor='w', pady=(5, 20))

        # --------------------- Confirmar Senha ----------------------
        ttk.Label(self.main_frame, text="Confirmar Senha:").pack(anchor='w', pady=(10, 0))
        self.confirmar_senha_entry = ttk.Entry(self.main_frame, width=40, style="TEntry", show='*')
        self.confirmar_senha_entry.pack(fill='x', ipady=5)

        # Checkbox Mostrar Senha (repetido para Confirmar Senha)
        tk.Checkbutton(self.main_frame, text="Mostrar Senha", variable=self.show_password_var, 
                       onvalue="1", offvalue="0", command=self.toggle_show_password, 
                       bg=master.cget('bg'), fg="white", selectcolor="#4CAF50").pack(anchor='w', pady=(5, 40))

        # --------------------- Botões ----------------------
        
        btn_frame = tk.Frame(self.main_frame, bg=master.cget('bg'))
        btn_frame.pack(fill='x', pady=(40, 10))
        
        btn_retornar = tk.Button(btn_frame, text="Retornar", command=self.voltar_callback,
                                     bg="#778899", fg="white", font=("Helvetica", 12),
                                     relief="groove", bd=2, padx=15, pady=5)
        btn_retornar.pack(side='left', padx=(0, 10))

        # Botão Finalizar
        self.btn_avancar = tk.Button(btn_frame, text="Finalizar Cadastro", command=self.coletar_e_enviar,
                                     bg="#2E8B57", fg="white", font=("Helvetica", 14, "bold"),
                                     relief="flat", bd=0, padx=20, pady=8)
        self.btn_avancar.pack(side='right')

    def toggle_show_password(self):
        # Alterna o modo de exibição (show='*' ou show='')
        if self.show_password_var.get() == "1":
            show_char = ""
        else:
            show_char = "*"
            
        self.senha_entry.config(show=show_char)
        self.confirmar_senha_entry.config(show=show_char)


    def coletar_e_enviar(self):
        senha = self.senha_entry.get()
        confirmar_senha = self.confirmar_senha_entry.get()
        
        if not senha or not confirmar_senha:
            messagebox.showwarning("Atenção", "Preencha ambos os campos de senha.")
            return
            
        if senha != confirmar_senha:
            messagebox.showwarning("Erro de Senha", "As senhas não coincidem.")
            return

        if len(senha) < 8:
            messagebox.showwarning("Erro de Senha", "A senha deve ter no mínimo 8 caracteres.")
            return

        # Aqui você junta com o que já tem salvo
        dados_final = dados_cadastro.copy()
        dados_final["senha"] = senha

        try:
            response = requests.post(
                URL_AFILIAR,
                json=dados_final
            )

            if response.status_code in [200, 201]:
                messagebox.showinfo("Sucesso", "Cadastro realizado! Agora aceite o Termo.")
                
                #Troca para a tela de termo depois de cadastrar
                self.enviar_callback()  # normalmente → self.mostrar_tela_termo()

            else:
                messagebox.showerror("Erro", f"Falha no cadastro: {response.text}")

        except Exception as e:
            messagebox.showerror("Erro", f"Erro de conexão: {str(e)}")


class Tela3(tk.Frame):
    def __init__(self, master, proximo_callback, voltar_callback):
        super().__init__(master, bg=master.cget('bg'))
        self.master = master
        self.proximo_callback = proximo_callback
        self.voltar_callback = voltar_callback
        self.pack(expand=True, fill="both", padx=30, pady=20) 

        # Frame de rolagem, pois a tela tem muitos campos
        canvas = tk.Canvas(self, bg=master.cget('bg'), highlightthickness=0)
        scrollbar = ttk.Scrollbar(self, orient="vertical", command=canvas.yview)
        scrollable_frame = tk.Frame(canvas, bg=master.cget('bg'))

        scrollable_frame.bind(
            "<Configure>",
            lambda e: canvas.configure(
                scrollregion=canvas.bbox("all")
            )
        )

        canvas.create_window((0, 0), window=scrollable_frame, anchor="nw")
        canvas.configure(yscrollcommand=scrollbar.set)
        
        canvas.pack(side="left", fill="both", expand=True)
        scrollbar.pack(side="right", fill="y")
        
        # --------------------- Título ----------------------
        tk.Label(scrollable_frame, text="Informações Adicionais e Formação", font=("Helvetica", 20, "bold"), bg=master.cget('bg'), fg="white").pack(pady=(10, 20))
        
        # --------------------- Seção: Sobre Você ----------------------
        tk.Label(scrollable_frame, text="— Detalhes Pessoais —", font=("Helvetica", 14), bg=master.cget('bg'), fg="white").pack(anchor='w', pady=(15, 5))
        
        # Sobre mim
        ttk.Label(scrollable_frame, text="Sobre mim:").pack(anchor='w', pady=(10, 0))
        self.sobre_mim_text = tk.Text(scrollable_frame, height=4, width=40, font=("Helvetica", 12))
        self.sobre_mim_text.pack(fill='x')

        # Área de atuação
        ttk.Label(scrollable_frame, text="Área de atuação principal:").pack(anchor='w', pady=(15, 0))
        self.area_atuacao_entry = ttk.Entry(scrollable_frame, width=40, style="TEntry")
        self.area_atuacao_entry.pack(fill='x', ipady=5)
        
        # Apoia em outra área?
        ttk.Label(scrollable_frame, text="Você apoiaria em outras áreas se necessário?").pack(anchor='w', pady=(20, 0))
        self.apoia_outras_causas_var = tk.StringVar(value="Não")
        
        frame_radio = tk.Frame(scrollable_frame, bg=master.cget('bg'))
        frame_radio.pack(anchor='w', pady=(5, 10))
        
        tk.Checkbutton(frame_radio, text="Sim", variable=self.apoia_outras_causas_var, onvalue="Sim", offvalue="Não", bg=master.cget('bg'), fg="white", selectcolor="#4CAF50").pack(side='left', padx=10)
        tk.Checkbutton(frame_radio, text="Não", variable=self.apoia_outras_causas_var, onvalue="Não", offvalue="Sim", bg=master.cget('bg'), fg="white", selectcolor="#4CAF50").pack(side='left', padx=10)
        
        # Habilidades
        ttk.Label(scrollable_frame, text="Habilidades (Separar por vírgula):").pack(anchor='w', pady=(15, 0))
        self.habilidades_entry = ttk.Entry(scrollable_frame, width=40, style="TEntry")
        self.habilidades_entry.pack(fill='x', ipady=5)

        # --------------------- Seção: Formação Acadêmica ----------------------
        tk.Label(scrollable_frame, text="— Formação Acadêmica —", font=("Helvetica", 14), bg=master.cget('bg'), fg="white").pack(anchor='w', pady=(25, 5))

        self.entries = {}
        
        # Campos de Formação 
        campos_formacao = [
            ("Curso:", "curso"),                  
            ("Nível de Formação (Ex: Bacharelado):", "nivelFormacao"), 
            ("Instituição:", "instituicao"),        
            ("Data de Início (AAAA-MM-DD):", "dataInicio"), 
            ("Data de Conclusão (AAAA-MM-DD):", "dataConclusao"), 
        ]
        
        for label_text, field_name in campos_formacao:
            ttk.Label(scrollable_frame, text=label_text).pack(anchor='w', pady=(10, 0))
            entry = ttk.Entry(scrollable_frame, width=50, style="TEntry")
            entry.pack(fill='x', ipady=5)
            self.entries[field_name] = entry

        # --------------------- Botões ----------------------
        
        # Frame para os botões
        btn_frame = tk.Frame(scrollable_frame, bg=master.cget('bg'))
        btn_frame.pack(fill='x', pady=(40, 10))
        
        # Botão Retornar
        btn_retornar = tk.Button(btn_frame, text="Retornar", command=self.voltar_callback,
                                     bg="#778899", fg="white", font=("Helvetica", 12),
                                     relief="groove", bd=2, padx=15, pady=5)
        btn_retornar.pack(side='left', padx=(0, 10))

        # Botão Avançar (Para a Tela Senha)
        self.btn_avancar = tk.Button(btn_frame, text="Avançar", command=self.coletar_e_avancar,
                                     bg="#2E8B57", fg="white", font=("Helvetica", 14, "bold"),
                                     relief="flat", bd=0, padx=20, pady=8)
        self.btn_avancar.pack(side='right')

    def coletar_e_avancar(self):
        dados_finais = {}
        
        # 1. Campos de Texto/Checkbox
        sobre_mim = self.sobre_mim_text.get("1.0", tk.END).strip()
        area_atuacao = self.area_atuacao_entry.get().strip()
        apoia_outras = self.apoia_outras_causas_var.get()
        habilidades = self.habilidades_entry.get().strip()

        # 2. Campos de Formação (Entries)
        todos_preenchidos = True
        for field, entry in self.entries.items():
            valor = entry.get().strip()
            if not valor:
                todos_preenchidos = False
            dados_finais[field] = valor
            
        # Adicionar campos de texto
        dados_finais.update({
            "sobreMim": sobre_mim,
            "areaAtuacao": area_atuacao,
            "apoiaOutrasCausas": apoia_outras,
            "Habilidades": habilidades,
        })
        
        if not sobre_mim or not area_atuacao or not habilidades or not todos_preenchidos:
            messagebox.showwarning("Atenção", "Por favor, preencha todos os campos da Tela 3.")
            return

        # Chama a função principal para ir para a próxima tela (Tela Senha)
        self.proximo_callback(dados_finais)
    #---------------------------------------------------------------------------------------

class TelaTermo(tk.Frame):
    def __init__(self, master, on_avancar, on_retornar):
        super().__init__(master, bg=master.cget('bg'))
        self.master = master
        self.on_avancar = on_avancar
        self.on_retornar = on_retornar

        self.pack(expand=True, fill="both", padx=30, pady=50)

        self.var_aceito_termo = tk.BooleanVar(value=False)

        tk.Label(self, text="Termo de Consentimento",
                 font=("Helvetica", 20, "bold"), bg=self.master.cget('bg'),
                 fg="white").pack(pady=20)

        texto = ("Ao prosseguir, você declara estar ciente de todo o conteúdo referido acima, "
                 "concordando com as condições de uso, termo de consentimento e contratual – "
                 "a aplicação à distância de descontos indiretos por meio do Cartão Mais Vale Social.")

        tk.Label(self, text=texto, wraplength=420, justify="left",
                 bg=self.master.cget('bg'), fg="white").pack(pady=20)

        tk.Checkbutton(self, text="Li e concordo com o Termo de Consentimento",
                       variable=self.var_aceito_termo, bg=self.master.cget('bg'),
                       fg="white", selectcolor="#4CAF50").pack(pady=20)

        btns = tk.Frame(self, bg=self.master.cget('bg'))
        btns.pack(pady=40, fill="x")

        tk.Button(btns, text="Retornar", bg="#778899", fg="white",
                  padx=15, pady=5, command=self.on_retornar).pack(side="left")

        tk.Button(btns, text="Avançar", bg="#2E8B57", fg="white",
                  padx=20, pady=8, command=self._avancar).pack(side="right")

    def _avancar(self):
        if not self.var_aceito_termo.get():
            messagebox.showwarning("Atenção", "Você precisa aceitar o termo para continuar.")
            return

        dados_cadastro["aceitou_termo"] = True

        # JSON completo enviado pela API
        payload = {
            "afiliacaoId": 1,
            "termoDeUsoId": 1,
            "consentimentoId": 3,
            "titulo": "Termo de Uso da Plataforma Rede+",
            "versao": "1.3.0",
            "link": "http://localhost:8080/afiliacoes/{id}/aprovacaoTermo",
            "itens": [
                {"id": 1, "descricao": "O usuário concorda com a coleta e uso de dados pessoais conforme descrito neste termo.", "obrigatorio": True},
                {"id": 2, "descricao": "O usuário declara ter lido e compreendido todas as cláusulas aqui apresentadas.", "obrigatorio": True},
                {"id": 3, "descricao": "O usuário aceita receber notificações sobre alterações nos termos de uso.", "obrigatorio": False},
                {"id": 4, "descricao": "A plataforma pode registrar logs de navegação para fins de segurança e auditoria.", "obrigatorio": True},
                {"id": 5, "descricao": "O usuário pode solicitar exclusão de seus dados pessoais conforme a LGPD.", "obrigatorio": False}
            ]
        }

        try:
            response = requests.post(URL_API_APROVACAO_TERMO, json=payload)

            if response.status_code in [200, 201]:
                messagebox.showinfo("Sucesso", "Termo aprovado! Um código foi enviado ao seu e-mail.")
                self.on_avancar()
            else:
                messagebox.showerror("Erro", f"Erro ao aprovar termo: {response.text}")

        except Exception as e:
            messagebox.showerror("Erro de Conexão", f"Falha ao enviar termo: {str(e)}")

    #---------------------------------------------------------------------------------------

class TelaValidarCodigo(tk.Frame):
    def __init__(self, master, on_validado, on_retornar):
        super().__init__(master, bg=master.cget('bg'))
        self.master = master
        self.on_validado = on_validado
        self.on_retornar = on_retornar

        self.pack(expand=True, fill="both", padx=30, pady=50)

        tk.Label(self, text="Validação de Código",
                font=("Helvetica", 20, "bold"), bg=self.master.cget('bg'),
                fg="white").pack(pady=20)

        tk.Label(self, text="Digite o código enviado ao seu email:",
                bg=self.master.cget('bg'), fg="white").pack(pady=(10, 5))

        self.codigo_digitado = ttk.Entry(self, width=20, font=("Helvetica", 16))
        self.codigo_digitado.pack(ipady=5, pady=10)

        btns = tk.Frame(self, bg=self.master.cget('bg'))
        btns.pack(pady=40, fill="x")

        tk.Button(btns, text="Retornar", bg="#778899", fg="white",
                padx=15, pady=5, command=self.on_retornar).pack(side="left")

        tk.Button(btns, text="Validar Código", bg="#2E8B57", fg="white",
                padx=20, pady=8, command=self._validar).pack(side="right")

    def _validar(self):
        codigo = self.codigo_digitado.get().strip()

        if not codigo:
            messagebox.showwarning("Atenção", "Digite o código para continuar.")
            return

        payload = {
            "email": dados_cadastro.get("email"),
            "codigo": codigo
        }

        try:
            response = requests.post(
                URL_API_VERIFICACAO_CODIGO,
                json=payload
            )

            if response.status_code in [200, 201]:
                messagebox.showinfo("Sucesso", "Código validado com sucesso!")
                self.on_validado()
            else:
                messagebox.showerror("Erro", f"Código incorreto: {response.text}")

        except Exception as e:
            messagebox.showerror("Erro de Conexão", f"Falha ao validar código: {str(e)}")


# --------------------- Execução Principal ----------------------

if __name__ == "__main__":
    root = tk.Tk()
    style = ttk.Style()
    style.theme_use('clam')
    
    app = AplicacaoCadastro(root)
    root.mainloop()