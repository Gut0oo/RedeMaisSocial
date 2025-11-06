import React, { useState } from 'react';
import { View, StyleSheet, Alert, Text, TouchableOpacity } from 'react-native';
import CustomInput from '../components/CustomInput';
import CustomButton from '../components/CustomButton';

const LoginScreen = () => { //tudo dentro dessa função definirá o que a tela mostra
    //useState() = eh a informação que o componente guarda enquanto está sendo usado
    // os Setter atualizam os valores
    const [email, setEmail] = useState(''); //valor digitado no campo 'email'
    const [senha, setSenha] = useState(''); //valor digitado no campo 'senha'
    const [loading, setLoading] = useState(false);

    const realizarLogin = () => {
        setLoading(true);
        setTimeout(() => {
            setLoading(false);
            alert(`Logou com: ${email}`);
        }, 1500);
    };

    return(
        <View style={styles.container}>
            <CustomInput
                placeholder="Email"
                value={email}
                onChangeText={setEmail}
            />  
            <CustomInput
                placeholder="Senha"
                value={senha}
                onChangeText={setSenha}
                secureTextEntry
            />
            <CustomButton title="Entrar" onPress={realizarLogin} loading={loading} disabled={loading}/>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        paddingHorizontal: 20,
        backgroundColor: '#f5f5f5',
    },
});

export default LoginScreen; //tornar a função e seu componentes disponivel para outros arquivos