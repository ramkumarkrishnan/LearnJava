/**
 * Demo TreeSet
 * The TreeSet class is similar to the hash set, with one added improvement.
 * A tree set is a sorted collection. You insert elements into the collection
 * in any order. When you iterate through the collection, the values are
 * automatically presented in sorted order. As the name of the class suggests,
 * the sorting is accomplished by a tree data structure. (The current
 * implementation uses a red-black tree. For a detailed description of
 * red-black trees see, for example, Introduction to Algorithms by Thomas
 * Cormen, Charles Leiserson, Ronald Rivest, and Clifford Stein,
 * The MIT Press, 2009.) Every time an element is added to a tree, it is
 * placed into its proper sorting position. Therefore, the iterator always
 * visits the elements in sorted order.
 */
import com.sun.source.tree.Tree;
import com.ramkrish.corejava.TreeItem;
import java.util.*;

public class JoshuaTree {
    public static void main(String[] args)  {
        var parts = new TreeSet<TreeItem>();
        parts.add(new TreeItem("Toaster", 7234));
        parts.add(new TreeItem("Grill", 4562));
        parts.add(new TreeItem("Modem", 9912));
        parts.add(new TreeItem("XBox", 6302));
        parts.add(new TreeItem("PS2", 1512));

        System.out.println("Print TreeSet (items added at random, but the tree will be formed sorted in partnumber order: ");
        System.out.println(parts);

        var sortByDescription = new TreeSet<TreeItem>(Comparator.comparing(TreeItem::getDescription));
        sortByDescription.addAll(parts);

        System.out.println("Print items from TreeSet sorted by description: ");
        System.out.println(sortByDescription);
    }
}