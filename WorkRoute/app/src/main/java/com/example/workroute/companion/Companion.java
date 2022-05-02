package com.example.workroute.companion;

import com.example.workroute.model.SubscribedUser;
import com.example.workroute.model.User;
import com.example.workroute.model.Viaje;

public class Companion {
    /***************************************************************************************
     * UTILIZAMOS ESTA CLASE PARA PODER RECOGER LOS DATOS DEL USARIO AL INICIAR LA APLIACACIÓN
     * Y ASI EVITAR TENER QUE CONSULTAR DICHOS DATOS EN FIRESTORE
     ***************************************************************************************/
    public static User user;
    public static SubscribedUser userSub;
    public static Viaje viaje;
}
