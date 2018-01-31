package ru.job4j.Collections;

import java.util.ArrayList;
import java.util.List;

import static jdk.nashorn.internal.objects.NativeBoolean.valueOf;

class ConvertList{

    public List<Integer> toList (int[][] array) {
        List<Integer> list = new ArrayList<Integer>();
        for (int[] i : array) {
            for (int j : i){
                list.add(j);
            }
        }
        return list;
    }



    public int[][] toArray (List<Integer> list, int rows) {
        int columns = 0;
        int i = 0;
        if (list.size() % rows != 0) {
            columns = (list.size()/rows) + 1;
            int NewCells = columns*rows-list.size();
            for (int j = 0; j <= NewCells; j++ ){
                list.add(0);
            }
        } else {
            columns = list.size()/rows;
        }
        int[][] NewArray = new int[rows][columns];
        int cell = 0;
        for (int a = 0; a < rows; a++) {
            for (int b = 0; b<columns; b++){
                NewArray[a][b] = list.get(i);
                i++;
            }
        }

        return NewArray;
    }




        }