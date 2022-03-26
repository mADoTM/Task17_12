package ru.vsu.cs.dolzhenkoms;

import javax.swing.table.DefaultTableModel;

public class ArrayUtils {
    public static String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < array.length; i++) {
            sb.append(array[i] + " ");
        }

        return sb.toString();
    }

    public static int[] toPrimitive(Integer[] objectArray) {
        int[] primitiveArray = new int[objectArray.length];

        for(int i = 0; i < objectArray.length; i++) {
            primitiveArray[i] = objectArray[i];
        }

        return primitiveArray;
    }

    public static Integer[] toObject(int[] primitiveArray) {
        Integer[] objectArray = new Integer[primitiveArray.length];

        for(int i = 0; i < primitiveArray.length; i++) {
            objectArray[i] = primitiveArray[i];
        }

        return objectArray;
    }

    public static int[] getArrayFromTable(DefaultTableModel model) {
        int[][] array = new int[model.getRowCount()][model.getColumnCount()];
        for(int i = 0; i < array.length; i++) {
            for(int j = 0; j < array[0].length; j++)
                array[i][j] = (int) Integer.parseInt((model.getValueAt(i,j).toString()));
        }
        System.out.println(ArrayUtils.arrayToString(array[0]));
        return array[0];
    }

    public static void fillTableModelBy2Array(DefaultTableModel model, int[][] array) {
        int rowCount = model.getRowCount();

        for(int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }

        model.setColumnCount(array[0].length);

        for(int i = 0; i < array.length; i++) {
            model.addRow(ArrayUtils.toObject(array[i]));
        }
    }
}
