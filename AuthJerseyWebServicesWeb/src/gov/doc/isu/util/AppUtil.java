package gov.doc.isu.util;

import java.io.Serializable;
import java.util.List;


/**
 * AppUtil is a utility class contains methods for string manipulation, object manipulation, and various other utility methods.
 *
 * @author Richard Salas JCCC
 * @author unascribed
 */
public class AppUtil implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2992489239779747996L;

    /***
     * This method return true if the given String is null or empty else returns false.
     *
     * @param s
     *        The String to check.
     * @return boolean True if null or empty, false otherwise.
     */
    public static boolean isNullOrEmpty(String s) {
        return (s == null || s.trim().equals("")) ? true : false;
    }

    /***
     * This method return true if the given String is null or empty else returns false.
     *
     * @param s
     *        The String to check.
     * @return boolean True if null or empty, false otherwise.
     */
    public static boolean isNullOrZero(String s) {
        return (s == null || s.trim().equals("0")) ? true : false;
    }

    /**
     * Used to return the word 'null' if the Object passed in equals null. This method called only from overridden toString() in object classes
     * and forms.
     *
     * @param o
     *        The Object to check if is null.
     * @return String The literal 'null' if is null, otherwise the value of the parameter.
     */
    public static String isNull(Object o) {
        return o == null ? "null" : String.valueOf(o);
    }

    /***
     * This method return true if the given List is null or empty else returns false.
     *
     * @param list
     *        The list to check.
     * @return boolean True if null or empty, false otherwise.
     */
    public static boolean isEmpty(List<?> list) {
        return (list != null && list.size() > 0) ? false : true;
    }

    /***
     * This method compares two alphanumeric String
     *
     * @param firstObjToCompare object to compare
     * @param secondObjToCompare object to compare
     * @return the value 0 if the argument object is equal to this object; a value less than 0 if this object is lexicographically less than the object argument; and a value greater than 0 if this object is lexicographically greater than the object argument.
     */
    public static int alphaNumericStringCompare(final Object firstObjToCompare, final Object secondObjToCompare) {
        if(firstObjToCompare == null || secondObjToCompare == null){
            return 0;
        }//end if

        String firstString = firstObjToCompare.toString();
        String secondString = secondObjToCompare.toString();

        int lengthFirstStr = firstString.length();
        int lengthSecondStr = secondString.length();

        int index1 = 0;
        int index2 = 0;

        while(index1 < lengthFirstStr && index2 < lengthSecondStr){
            char ch1 = firstString.charAt(index1);
            char ch2 = secondString.charAt(index2);

            char[] space1 = new char[lengthFirstStr];
            char[] space2 = new char[lengthSecondStr];

            int loc1 = 0;
            int loc2 = 0;

            do{
                space1[loc1++] = ch1;
                index1++;

                if(index1 < lengthFirstStr){
                    ch1 = firstString.charAt(index1);
                }else{
                    break;
                }//end if
            }while(Character.isDigit(ch1) == Character.isDigit(space1[0]));

            do{
                space2[loc2++] = ch2;
                index2++;

                if(index2 < lengthSecondStr){
                    ch2 = secondString.charAt(index2);
                }else{
                    break;
                }//end if
            }while(Character.isDigit(ch2) == Character.isDigit(space2[0]));

            String str1 = new String(space1);
            String str2 = new String(space2);

            int result;

            if(Character.isDigit(space1[0]) && Character.isDigit(space2[0])){
                Long firstNumberToCompare = new Long(Long.parseLong(str1.trim()));
                Long secondNumberToCompare = new Long(Long.parseLong(str2.trim()));
                result = firstNumberToCompare.compareTo(secondNumberToCompare);
            }else{
                result = str1.compareTo(str2);
            }//end if

            if(result != 0){
                return result;
            }//end if
        }//end if
        return lengthFirstStr - lengthSecondStr;
    }

}
