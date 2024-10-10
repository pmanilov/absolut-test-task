import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class Product {
    private final String name;
    private final Set<Product> ingredients;
    private Product parent;

    public Product(String name) {
        this.name = name;
        this.ingredients = new HashSet<>();
        this.parent = null;
    }

    public boolean addProduct(Product product) {
        if (ingredients.contains(product)) {
            return false;
        }
        Set<Product> allIngredients = new HashSet<>();
        product.collectAllIngredients(allIngredients);
        for (Product current = this; current != null; current = current.getParent()) {
            if (allIngredients.contains(current)) {
                return false;
            }
        }
        product.setParent(this);
        ingredients.add(product);
        return true;
    }

    private void collectAllIngredients(Set<Product> allIngredients) {
        for (Product ingredient : ingredients) {
            if (allIngredients.add(ingredient)) {
                ingredient.collectAllIngredients(allIngredients);
            }
        }
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
