package Modelos;

public class UsuarioMd {

    private String UsrCodigo;
    private String UsrUsuario;
    private String UsrRol;
    private String UsrUsuarioCrea;
    private String UsrNombre;
    private String UsrPalabra;
    private String UsrStatus;
    private String UsrClave;
    private String Contacto;

    public String getUsrClave() {
        return UsrClave;
    }

    public void setUsrClave(String UsrClave) {
        this.UsrClave = UsrClave;
    }

    public String getUsrCodigo() {
        return UsrCodigo;
    }

    public void setUsrCodigo(String UsrCodigo) {
        this.UsrCodigo = UsrCodigo;
    }

    public String getUsrUsuario() {
        return UsrUsuario;
    }

    public void setUsrUsuario(String UsrUsuario) {
        this.UsrUsuario = UsrUsuario;
    }

    public String getUsrRol() {
        return UsrRol;
    }

    public void setUsrRol(String UsrRol) {
        this.UsrRol = UsrRol;
    }

    public String getUsrUsuarioCrea() {
        return UsrUsuarioCrea;
    }

    public void setUsrUsuarioCrea(String UsrUsuarioCrea) {
        this.UsrUsuarioCrea = UsrUsuarioCrea;
    }

    public String getUsrNombre() {
        return UsrNombre;
    }

    public void setUsrNombre(String UsrNombre) {
        this.UsrNombre = UsrNombre;
    }

    public String getUsrPalabra() {
        return UsrPalabra;
    }

    public void setUsrPalabra(String UsrPalabra) {
        this.UsrPalabra = UsrPalabra;
    }

    public String getUsrStatus() {
        return UsrStatus;
    }

    public void setUsrStatus(String UsrStatus) {
        this.UsrStatus = UsrStatus;
    }

    public String getContacto() {
        return Contacto;
    }

    public void setContacto(String Contacto) {
        this.Contacto = Contacto;
    }
    
    public UsuarioMd() {
    }

}
