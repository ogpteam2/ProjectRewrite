package rpg.inventory;

/**
 * Interface declaring that the given object can have a parent.
 * Provides functionality to set and get the parent of the object, to be used by
 * Parent interface implementers.
 * The parent is expected to manage and maintain the consistency of the relationship
 * and thus only basic checking is expected to be done at the end of the implementers
 * of this interface.
 */
public interface hasParent {

    void setParent(Parent parent);

    Parent getParent();
}
