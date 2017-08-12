package rpg.inventory;

/**
 * Interface declaring that the given object can have a parent.
 * Having a parent means the relationship between item and parent is bidirectional.
 * Provides functionality to set and get the parent of the object, to be used by
 * Parent interface implementers.
 * The parent is expected to manage and maintain the consistency of the relationship
 * and thus only basic checking is expected to be done at the end of the implementers
 * of this interface.
 */
interface hasParent {

    void setParent(Parent parent);

    Parent getParent();

    boolean hasParent();

    /**
     * Defines what happens if the item is dropped to the ground. Necessary as some items may not exist on the
     * ground after being contained in a container.
     */
    void drop();
}
