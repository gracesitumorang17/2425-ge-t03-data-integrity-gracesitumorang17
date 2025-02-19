package academic.model;

/**
 * @author 12S23008 Ranty Insen Pakpahan
 * @author 12S23048 Grace Caldera Situmorang
 */
public class Course {

   private final String code;
   private final String courseName;
   private final int kredit;
   private final String grade;

   public Course (String code, String courseName, int kredit, String grade){
    this.code = code;
    this.courseName = courseName;
    this.kredit = kredit;
    this.grade = grade;
   }

   public String getCode(){
    return code;
   }

   public String getCourseName(){
    return  courseName;
   }

   public int getKredit(){
    return kredit;
   }

   public String  getGrade(){
    return grade;
   }

   @Override
    public String toString() {
     return code + "|" + courseName + "|" + kredit + "|" + grade;
    }
}
