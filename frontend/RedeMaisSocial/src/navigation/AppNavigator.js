import React from 'react';
import { createDrawerNavigator } from '@react-navigation/drawer';
import LoginScreen from '../screens/LoginScreen';


const Drawer = createDrawerNavigator();


//StackScrenn = define cada tela
const AppNavigator = () => {
    return ( //initialRouteName = Define a tela de inicio
    <Drawer.Navigator initialRouteName="Login">
      <Drawer.Screen name="Login" component={LoginScreen} />
      {/* No futuro, outras telas */}
    </Drawer.Navigator>
    );
};

export default AppNavigator;
