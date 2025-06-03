package com.fia.base.model.DTO;

public class UsuarioLoginDto {
    private String correo;
    private String contrasena;
    
    // Constructor vacío
    public UsuarioLoginDto() {
    }
    
    // Constructor con parámetros
    public UsuarioLoginDto(String correo, String contrasena) {
        this.correo = correo;
        this.contrasena = contrasena;
    }
    
    // Getters y Setters
    public String getCorreo() {
        return correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String getContrasena() {
        return contrasena;
    }
    
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    // Método toString (opcional)
    @Override
    public String toString() {
        return "UsuarioLoginDto{" +
               "correo='" + correo + '\'' +
               ", contrasena='[PROTEGIDO]'" +
               '}';
    }
}