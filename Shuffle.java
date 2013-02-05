/////////////////////////////////////////////////////////////////
// Filename: Shuffle.java
// Author: Fernando Carrillo (fcarril1)
// Class: CMPS 101 Patrick Tantalo
// Assignment: pa1
//
// Description: The Shuffle.java program reads in a file for
//    permutations. It then makes a default list and applies the
//    permutation to it. It repeats this process until it reverts
//    back to its default state and prints how many iterations it 
//    took to do so.
/////////////////////////////////////////////////////////////////

import java.io.*;
import java.util.Scanner;
import java.lang.Integer;

class Shuffle{
    public static void main(String[] args) throws IOException{
        Scanner in = null;
        PrintWriter out = null;
        String line = null, print = null;
        String[] token = null;
        int i, n=0, t,  lineNumber = 0, order = 0;

        if(args.length < 2){
            System.out.println("Usage: Shuffle infile outfile");
            System.exit(1);
        }

        in = new Scanner(new File(args[0]));
        out = new PrintWriter(new FileWriter(args[1]));

        List P = new List();
        List L = new List();
        List Lkey = null;

        // Gets number of permutations in the file in the first line
        if( in.hasNextLine() ){
            lineNumber++;
            line = in.nextLine()+" ";
            token = line.split("\\s+");
            n = Integer.parseInt(token[0]);
        }

        for(n=n; n>0; n--){
            line = in.nextLine()+" ";
            token = line.split("\\s+");

            //iterates through a single permutation and inserts every token into List P
            for( i = 0; i<token.length; i++){
                P.insertBack(Integer.parseInt(token[i]));
            }
     
            //Generates a new List depending on how long P is.
            L = makeList(token.length);
            Lkey = L.copy();

            Shuffle( L, P );
            order = 1;

            //prints List P and L
            //out.println(P.toString());
            out.print("(" + L.toString()+ ")");

            //shuffles until list L is back to its original state.
            //and prints out order. 
            while(!L.equals(Lkey)){
                order++;
                Shuffle( L, P );
            }
             out.println("    order=" + order );

            //Empties out permutation and generated List;
            P.makeEmpty();
            L.makeEmpty();
        }

        in.close();
        out.close();
    }

    // Applies permutation List P to List L once
    public static void Shuffle( List L, List P ){
        int posTo, offset, moveData;
        P.moveTo(0);
        L.moveTo(0);
        offset = L.getLength();
        while (!P.offEnd()){
            posTo = P.getCurrent();
            moveData = L.getFront();
            L.moveTo(offset-1);
            for(int i = 1; i < posTo; i++){
                L.moveNext();
            }
            L.insertAfterCurrent(moveData);
            L.deleteFront();
            offset--;
            P.moveNext();
        }
    }

    public static List makeList( int i ){
        List temp = new List();
        for (int j = 1; j <= i ;j++){
            temp.insertBack(j);
        }
        return temp;
    }

}
