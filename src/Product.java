import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.ArrayDeque;
import java.util.Deque;

class Product {
    private final String name;
    private final Set<Product> ingredients;
    private Product parent;

    public Product(String name) {
        this.name = name;
        this.ingredients = new HashSet<>();
    }

    public boolean addProduct(Product product) {
        if (product == null || ingredients.contains(product)) {
            return false;
        }
        Set<Product> allIngredients = product.collectAllIngredients();
        for (Product current = this; current != null; current = current.getParent()) {
            if (allIngredients.contains(current)) {
                return false;
            }
        }
        product.setParent(this);
        ingredients.add(product);
        return true;
    }

    private Set<Product> collectAllIngredients() {
        Set<Product> allIngredients = new HashSet<>();
        Deque<Product> deque = new ArrayDeque<>(ingredients);
        while (!deque.isEmpty()) {
            Product current = deque.poll();
            if (allIngredients.add(current)) {
                deque.addAll(current.ingredients);
            }
        }
        return allIngredients;
    }

    private void setParent(Product parent) {
        this.parent = parent;
    }

    public Product getParent() {
        return this.parent;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product other = (Product) obj;
        return name.equalsIgnoreCase(other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase());
    }
}
