package Mathematics.Groups;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Group<T>  {
    private int order;
    private int identityRowIndex;
    private ArrayList<ArrayList<T>> abstractOpTable;
    private int numIdentityCandidates;
    private int[] identityCandidates;


    public Group(T[][] opTable) throws GroupException {
        buildOpTable(opTable);
    }

    private boolean isOpTableSquare(T[][] opTable){
        int numRows = opTable.length;
        for (T[] row : opTable) {
            if (row.length != numRows) {
                return false;
            }
        }
        return true;
    }

    private void buildOpTable(T[][] opTable) throws GroupException {
        int order = opTable.length;
        if (!isOpTableSquare(opTable)) {
            throw new GroupException(Errors.ErrorCode.OP_TABLE_NOT_SQUARE);
        }
        HashMap<T,Integer> elementCount = buildHashMapOfElements(opTable);
        if (elementCount.size() != order) {
            throw new GroupException(Errors.ErrorCode.OP_TABLE_IS_NOT_CLOSED);
        }
        if (!isElementUniqueInRows(elementCount, opTable)) {
            throw new GroupException(Errors.ErrorCode.OP_TABLE_ROW_CONTAINS_DUPLICATES);
        }
        buildIdentityCandidates(opTable);
        if (getNumIdentityCandidates() == 0) {
            throw new GroupException(Errors.ErrorCode.NO_FEASIBLE_IDENTITY);
        }
        setIdentityElementIndex(this.identityCandidates[0]);
        setGroupOrder(opTable.length);
        this.abstractOpTable = new ArrayList<ArrayList<T>>(this.order);
        for (T[] row : opTable) {
            ArrayList<T> opRow = new ArrayList<>(this.order);
            opRow.addAll(Arrays.asList(row));
            this.abstractOpTable.add(opRow);
        }

    }
    public void setGroupOrder(int order) throws GroupException {
        if (order < 1) {
            throw new GroupException(Errors.ErrorCode.GROUP_SIZE_NOT_SET);
        }
        this.order = order;
    }
    public void setOperationTable(T[][] opTable) throws GroupException {
        buildOpTable(opTable);

    }

    public T getIdentityElement() {
        return this.abstractOpTable.get(identityRowIndex).get(identityRowIndex);
    }

    private int getIndexOfElement(T element, T[] testArray) {
        for (int i=0; i< testArray.length; i++) {
            if (testArray[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }
    private boolean identityIndexAdmitsAssociative(int identityRowIndex, T[][] opTable) {
        T[] elementMap = opTable[identityRowIndex];

        for (int indexA=0; indexA< opTable.length; indexA++) {
            for (int indexB=0; indexB< opTable.length; indexB++) {
                int ABindex = getIndexOfElement(opTable[indexA][indexB],elementMap);
                for (int indexC=0; indexC< opTable.length; indexC++) {
                    int BCindex = getIndexOfElement(opTable[indexB][indexC],elementMap);
                    if (opTable[ABindex][indexC] != opTable[indexA][BCindex]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    private boolean testIdentityRowIndex(int identityRowIndex, T[][] opTable) {
        T identityElement = opTable[identityRowIndex][identityRowIndex];
        for (int leftOperandIndex = 0; leftOperandIndex < opTable.length; leftOperandIndex++) {
            if (!opTable[identityRowIndex][leftOperandIndex].equals(opTable[leftOperandIndex][identityRowIndex])) {
                return false;
            }
            boolean inverseFound = false;
            for (int rightOperandIndex = 0; rightOperandIndex<opTable.length; rightOperandIndex++) {
                if (opTable[leftOperandIndex][rightOperandIndex].equals(identityElement)
                        && opTable[rightOperandIndex][leftOperandIndex].equals(identityElement) ){
                    inverseFound = true;

                    break;
                }
            }
            if (!inverseFound) {
                return false;
            }
        }
        return true;
    }
    private void buildIdentityCandidates(T[][] opTable) {
        int identityCandidateCount = 0;
        boolean[] identityCandidateBooleanArray = new boolean[opTable.length];
        for (int identityRowIndex=0; identityRowIndex< opTable.length; identityRowIndex++) {
            identityCandidateBooleanArray[identityRowIndex] = false;
            if (testIdentityRowIndex(identityRowIndex,opTable) && identityIndexAdmitsAssociative(identityRowIndex,opTable)) {
                identityCandidateBooleanArray[identityRowIndex] = true;
                identityCandidateCount++;
            }
        }
        this.numIdentityCandidates = identityCandidateCount;
        this.identityCandidates = new int[identityCandidateCount];
        int currentIndex = 0;
        for (int identityRowIndex = 0; identityRowIndex< opTable.length; identityRowIndex++) {
            if (identityCandidateBooleanArray[identityRowIndex]) {
                this.identityCandidates[currentIndex] = identityRowIndex;
                System.out.println("Identity Candidate " + identityRowIndex);
                currentIndex++;
            }
        }
    }

    public void setIdentityElementIndex(int identityRowIndex) throws GroupException {
        for(int index : this.identityCandidates) {
            if (index == identityRowIndex) {
                this.identityRowIndex = identityRowIndex;
                System.out.println("Set an identity " + identityRowIndex);
                return;
            }
        }
        throw new GroupException(Errors.ErrorCode.ROW_INDEX_NOT_FEASIBLE_AS_IDENTITY);
    }

    public int getNumIdentityCandidates() {
        return this.numIdentityCandidates;
    }
    public int[] getIdentityCandidateIndexes() {
        return this.identityCandidates;
    }

    public int getOrder() {
        return this.order;
    }

    private HashMap<T,Integer> buildHashMapOfElements(T[][] opTable) {
        int order = opTable.length;
        HashMap<T,Integer> elementCount = new HashMap<T, Integer>();
        for (T[] row : opTable) {
            for (T element : row) {
                elementCount.put(element, 0);
            }
        }
        return elementCount;
    }
    private boolean isElementUniqueInRows(HashMap<T,Integer> elementCount, T[][] opTable) {
        int expectedCount = 0;
        for (T[] row : opTable) {
            for (T element : row) {
                if (elementCount.get(element) != expectedCount) {
                    return false;
                }
                elementCount.put(element, expectedCount + 1);
            }
            expectedCount++;
        }
        return true;
    }
}
