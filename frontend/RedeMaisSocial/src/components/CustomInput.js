import React from 'react';
import { TextInput, Text, View, StyleSheet } from 'react-native'; 

const CustomInput = ({
    value, //valor do input
    onChangeText, //função chamada toda vez que o usuario digitar algo
    placeholder, //texto que vai aparecer quando o imput estiver vazio
    secureTextEntry, //esconderá o texto digitado(senha) - faz isso quando estiver true
    placeholderTextColor
}) => {
    return (
        <View >
            <TextInput //Campo que o usert vai digitar
                style={[styles.input]}
                value={value}
                onChangeText={onChangeText}
                placeholder={placeholder}
                secureTextEntry={secureTextEntry}
                placeholderTextColor="#999"
            />
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        marginVertical: 5
    },
    input: {
        borderWidth: 1,
        borderColor: '#000',
        padding: 10,
        borderRadius: 5,
        backgroundColor: '#fff',
        fontSize: 16,
    }
});

export default CustomInput;