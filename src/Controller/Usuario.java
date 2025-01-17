package Controller;

import DAO.UsuarioDAO;

public class Usuario {
    private String id;
    private String password;

    // Constructor
    public Usuario( String id, String password) {
        this.id = id;
        this.password = password;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public boolean validar() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        return usuarioDAO.validar(this);
    }
}