package academic.model;

/**
 * @author 12S23008 Ranty Insen Pakpahan
 * @author 12S23048 Grace Caldera Situmorang
 */
public class Student {
    private String nim;
    private String nama;
    private int tahun;
    private String jurusan;

    public Student (String nim, String nama, int tahun, String jurusan){
        this.nim = nim;
        this.nama = nama;
        this.tahun = tahun;
        this.jurusan = jurusan;
    }

    public String getNim(){
        return nim;
    }
    public String getNama(){
        return nama;
    }
    public int getTahun(){
        return tahun;
    }
    public String getJurusan(){
        return jurusan;
    }

 @Override
 public String toString(){
    return nim+"|"+nama+"|"+tahun+"|"+jurusan;
 }

}