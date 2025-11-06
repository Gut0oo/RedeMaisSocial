import React from 'react';
import { TouchableOpacity, Text, ActivityIndicator, StyleSheet } from 'react-native'; //TouchableOpacity = botão clicavel

const CustomButton = ({
    title, //texto do botão
    onPress, //executa quando clica nele 
    loading, //mostra o carregamento
    disabled //desabilita o botão caso necessario (Previnir multiplos clicks)
}) => {
    return(
        <TouchableOpacity //botão clicavel
            onPress={onPress} //função a ser chamada quando botão ser clicado

            //      estilo padrão | Aplica diferente estilo quando estiver desativado
            style={[styles.button, disabled && styles.disabledButton]}
            disabled={disabled || loading} //Se o botão estiver carregando, ele será desativado
        >
            {loading ? ( //Se estiver carregando faz isso
                <ActivityIndicator color={'#aaa'} />
            ) : ( //se não fai isso
                <Text style={styles.text}>{title}</Text>
            )}
        </TouchableOpacity>
    );
};

const styles = StyleSheet.create({
    button: { //estilo do botão
        backgroundColor: '#FFFFFF',
        borderRadius: 65,
        marginVertical: 5, //Espaço entre os outros componentes
        paddingVertical: 10, //altura do botão
        paddingHorizontal: 20, //largura do botão
        alignItems: 'center', //alinha o conteudo horizontalmente
        justifyContent: 'center', //centraliza verticalmente

        //Configuração do sombreamento
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.3,
        shadowRadius: 4,
    },

    text : { //estilo do texto do botão
        color: '#000',
        fontSize: 22,
        fontWeight: 'bold' //fonte em negrito
    },
    
    disabledButton: { //estilo para quando o botão estiver desabilitado
        backgroundColor: '#aaa' //indica desativado
    }
});

export default CustomButton;