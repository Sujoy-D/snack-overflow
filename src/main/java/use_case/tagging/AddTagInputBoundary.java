package use_case.tagging;

/**
 * Input boundary for the Add Tag use case.
 */
public interface AddTagInputBoundary {
    void execute(AddTagInputData tagData);
}
