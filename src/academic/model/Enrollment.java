package academic.model;

/**
 * @author 12S23008 Ranty Insen Pakpahan
 * @author 12S23048 Grace Caldera Situmorang
 */
public class Enrollment {
    private final String nim;
    private final String kodeMatkul;
    private final String tahunAjaran;
    private final String semester;
    private String status;

    public Enrollment(String nim, String kodeMatkul, String tahunAjaran, String semester, String status){
        this.kodeMatkul = kodeMatkul;
        this.nim = nim;
        this.tahunAjaran = tahunAjaran;
        this.semester = semester;
        this.status = status;
    }

    
    public String getKodeMatkul(){
        return kodeMatkul;
    }
    public String getNim(){
        return nim;
     }
    public String getTahunAjaran(){
        return tahunAjaran;
    }
    public String getSemester(){
        return semester;  
    }
    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    @Override
    public String toString() {
        return kodeMatkul + "|" + nim + "|" + tahunAjaran + "|" + semester + "|" + status;
    }
}
