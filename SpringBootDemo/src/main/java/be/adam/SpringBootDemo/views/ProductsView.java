package be.adam.SpringBootDemo.views;

import be.adam.SpringBootDemo.models.Category;
import be.adam.SpringBootDemo.models.Product;
import be.adam.SpringBootDemo.repositories.CategoryRepository;
import be.adam.SpringBootDemo.services.ProductService;
import org.primefaces.context.RequestContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.Optional;

@ManagedBean
@ViewScoped
@RequestScoped
public class ProductsView implements Serializable {
    private Iterable<Product> products;
    private Product selectedProduct;
    private Iterable<Category> categories;
    private MenuModel menuModel;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;

    public ProductsView() {
    }

    @PostConstruct
    public void init()
    {
        products = null;

        // haal category id op uit parameter
        String category = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("category");

        // geef alle products mee aan view indien er geen category parameter is
        if(category == null || category.isEmpty()) {
            products = productService.findAll();
        }
        else {
            Optional<Category> cat = categoryRepository.findById(Long.valueOf(category));

            if(cat.isPresent()) {
                Category category1 = cat.get();
                products = category1.getProducts();
            }
        }

        // maak menu aan
        menuModel = new DefaultMenuModel();
        categories = getCategories();

        for(Category cat : categories) {
            DefaultMenuItem item = new DefaultMenuItem(cat.getName());
            item.setUrl("products.xhtml?category=" + cat.getId());
            menuModel.addElement(item);
        }

    }

    public Iterable<Product> getProducts() {
        return products;
    }

    public Iterable<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public void setService(ProductService productService) {
        this.productService = productService;
    }

    public void setSelectedProduct(Product product) {
        this.selectedProduct = productService.find(String.valueOf(product.getId()));
    }

    public Product getSelectedProduct() {
        return this.selectedProduct;
    }

    public MenuModel getMenuModel() {
        return menuModel;
    }
}
