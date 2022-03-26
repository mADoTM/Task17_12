package ru.vsu.cs.dolzhenkoms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortState {
    private List<SortState> steps;

    private int[] array;

    private int i;
    private int j;

    private boolean isSorted;
    private boolean isStepSwapping;

    public SortState(int[] array) {
        this.steps = new ArrayList<>();
        this.array = array;
        this.i = 0;
        this.j = 1;
        this.isSorted = false;
        this.isStepSwapping = false;
    }

    private SortState(SortState state) {
        this.array = Arrays.copyOf(state.array, state.array.length);
        this.steps = state.steps;
        this.i = state.i;
        this.j = state.j;
        this.isSorted = state.isSorted;
        this.isStepSwapping = state.isStepSwapping;
    }

    public void gnomeSort() {
        while(i < array.length - 1) {
            if(array[i] <= array[j]) {
                i++; j++;
                isStepSwapping = false;
            }
            else if(array[i] > array[j]) {
                int temp = array[j];
                array[j] = array[i];
                array[i] = temp;

                isStepSwapping = true;
                if(i > 0) {
                    i--; j--;
                }
            }

            if(i == array.length - 1)
                isSorted = true;
            steps.add(new SortState(this));
        }
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public boolean isSorted() {
        return isSorted;
    }

    public boolean isStepSwapping() {
        return isStepSwapping;
    }

    public int[] getArray() {
        return array;
    }

    public List<SortState> getSteps() {
        return steps;
    }

    public void printInfo() {
        System.out.println(ArrayUtils.arrayToString(this.array));
        System.out.println("i = " + i + " j= " + j);
        System.out.println("Массив отсортирован - " + isSorted);
    }
}
