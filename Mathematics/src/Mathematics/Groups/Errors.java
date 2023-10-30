package Mathematics.Groups;

public class Errors {
    public enum ErrorCode {
        OP_TABLE_NOT_SQUARE,
        GROUP_SIZE_NOT_SET,
        TOO_MANY_DISTINCT_SYMBOLS,
        GROUP_ORDER_MUST_BE_POSITIVE,
        OP_TABLE_IS_NOT_CLOSED,
        ROW_INDEX_NOT_FEASIBLE_AS_IDENTITY,
        NO_FEASIBLE_IDENTITY,
        OP_TABLE_EMPTY,
        OP_TABLE_ROW_CONTAINS_DUPLICATES

    }

    public static String errorString(ErrorCode errorCode) {
        switch (errorCode) {
            case OP_TABLE_NOT_SQUARE:
                return "The supplied 2D array (operation table) must be square (same number of rows and columns)";
            case GROUP_SIZE_NOT_SET:
                return "The order of the group has not been set.";
            case TOO_MANY_DISTINCT_SYMBOLS:
                return "There are too many distinct symbols(elements) in the operation table.";
            case GROUP_ORDER_MUST_BE_POSITIVE:
                return "The order of the group must be an integer value greater than zero.";
            case OP_TABLE_IS_NOT_CLOSED:
                return "Too Many Distinct Elements In Operation Table. Fails closure requirement for group.";
            case ROW_INDEX_NOT_FEASIBLE_AS_IDENTITY:
                return "The index provided does not represent a valid identity map index";
            case NO_FEASIBLE_IDENTITY:
                return "The operation table provided does not contain a feasible identity element";
            case OP_TABLE_EMPTY:
                return "The operation table can not be empty.";
            case OP_TABLE_ROW_CONTAINS_DUPLICATES:
                return "Each element in a group row must be unique. A row in the operation table contains duplicates.";
            case null, default:
                return "Unknown Error Has Occurred.";
        }
    }
}
