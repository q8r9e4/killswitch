/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.killswitch;

/**
 *
 * @author q8r9e4
 */
public class util {
           /**
         * <p>This method splits a given String object into an Array of Strings of a given static length.</p>
         *
         * <p>Contract: The string slice stored in the last index of the returned array may have a shorter length as the specified. This method DOES NOT fill the last string with spaces.</p>  
         *  
         * @param inputString the string object to be sliced into an Array of Strings
         * @param sliceLength the maximal length of every single slice
         * @return Array of strings based on the given inputString argument, every slice has a maximal length as specified by the sliceLength argument.
         */
        public static String[] slice(String inputString, int sliceLength) {
     
            if (inputString.length()<sliceLength){
                String[] ret = new String[1];
                ret[0] = inputString;
                return ret;
            }
            // Calculate the size of the array of calculates the last index for code readability
            int arraySize = (inputString.length() / sliceLength) + (inputString.length() % sliceLength != 0 ? 1 : 0);
            int lastArrayIndex = arraySize - 1;
     
            // creates the array object, this is the object to be returned
            String[] resultingArray = new String[arraySize];
     
            // simply loop through all array indexes, every loop will calculate the needed offset for reading string slices.
            // this loop will feed all array indexes with the exception of the last one.
            for (int i = 0; i < lastArrayIndex; i++) {
                int sliceOffset = i * sliceLength;
                resultingArray[i] = inputString.substring(sliceOffset, sliceOffset + sliceLength);
            }
     
            // the last index needs special treatment, otherwise you'll get an ArrayOutOfBoundsException while reading from inputString
            resultingArray[lastArrayIndex] = inputString.substring((lastArrayIndex) * sliceLength);
     
            // done
            return resultingArray;
     
        }

}
