package com.example.agromanager2_0;

public class UserProfileStorage {
    private static String nombre = "Nombre por defecto";
    private static String apellido = "Apellido por defecto";
    private static String correo = "correo@ejemplo.com";
    private static String password = "contraseña123";

    public static String getNombre() {
        return nombre;
    }

    public static void setNombre(String nombre) {
        UserProfileStorage.nombre = nombre;
    }

    public static String getApellido() {
        return apellido;
    }

    public static void setApellido(String apellido) {
        UserProfileStorage.apellido = apellido;
    }

    public static String getCorreo() {
        return correo;
    }

    public static void setCorreo(String correo) {
        UserProfileStorage.correo = correo;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        UserProfileStorage.password = password;
    }
}
