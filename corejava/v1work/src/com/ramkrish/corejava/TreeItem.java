/**
 * Implement TreeItem for JoshuaTree demo of TreeMap
 */
package com.ramkrish.corejava;
import com.sun.source.tree.Tree;

import java.util.Objects;

public class TreeItem implements Comparable<TreeItem>
{
    private String description;
    private int partNumber;

    /**
     * Constructs an item.
     * @param aDescription the item's description
     * @param aPartNumber the item's part number
     */
    public TreeItem(String aDescription, int aPartNumber)  {
        description = aDescription;
        partNumber = aPartNumber;
    }

    /**
     * Gets the description of this item.
     * @return the description
     */
    public String getDescription()  {
        return description;
    }

    public String toString()  {
        return "[description=" + description + ", partNumber=" + partNumber + "]";
    }

    public boolean equals(Object otherObject)  {
        if (this == otherObject) return true;
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;
        var other = (TreeItem) otherObject;
        return Objects.equals(description, other.description) && partNumber == other.partNumber;
    }

    public int hashCode()  {
        return Objects.hash(description, partNumber);
    }

    public int compareTo(TreeItem other)  {
        int diff = Integer.compare(partNumber, other.partNumber);
        return (diff != 0) ? diff : description.compareTo(other.description);
    }
}
