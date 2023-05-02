
package Modelos;

public class CategoriaMd {
    private String id;
    private String Descripcion;
    private String Padre;
   

    public CategoriaMd() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getPadre() {
        return Padre;
    }

    public void setPadre(String Padre) {
        this.Padre = Padre;
    }

}
