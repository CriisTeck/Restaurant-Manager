package controllers;

import exceptions.ConsumableTypeNotFound;
import exceptions.NotSelectedCellException;
import exceptions.ProductNotFoundException;
import exceptions.EmptyFieldsException;
import models.*;
import views.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

public class ControllerApp implements ActionListener, KeyListener {

    private PrincipalWindow view;
    private WindowSell viewSell;
    private WindowDelivery viewDelivery;
    private WindowManager viewManager;
    private WindowAddIngredient viewAddIngredient;
    private WindowAddFoodPackage viewAddFoodPackage;
    private WindowAddDish viewAddDish;
    private WindowAddFoodPackage viewEditFoodPackage;
    private WindowAddDish viewEditDish;
    private WindowAddIngredient viewEditIngredients;
    private Restaurant model;

    public ControllerApp() {
        try {
            view = new PrincipalWindow(this);
            model = new Restaurant();
            viewSell = new WindowSell(this, this, view, true);
            viewDelivery = new WindowDelivery(this, this, view, true);
            viewManager = new WindowManager(this, this, view);
            viewAddIngredient = new WindowAddIngredient(this, this, false, viewManager);
            viewAddFoodPackage = new WindowAddFoodPackage(this, this, false, viewManager);
            viewAddDish = new WindowAddDish(this, this, false, viewManager);
        } catch (IOException e) {
            view.showError(e.getMessage());

        }
    }

    public void initItems(){
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            switch (e.getActionCommand()) {
                case "Exit":
                    System.exit(0);
                    break;
                case "Sell":
                    showWindowSellProduct();
                    break;
                case "Delivery":
                    showWindowDelivery();
                    break;
                case "Manager":
                    showWindowManager();
                    break;
                case "SELL_PRODUCT":
                    sell();
                    break;
                case "DELIVERY_PRODUCT":
                    sellDelivery();
                    break;
                case "EDIT_PRODUCT_MANAGER":
                    showEditManagerConsumable();
                    break;
                case "ADD_PRODUCT_MANAGER":
                    addProductManager();
                    break;
                case "EDIT_DISH":
                    editDish();
                    break;
                case "EDIT_FOODPACKAGE":
                    editFoodPackage();
                    break;
                case "EDIT_INGREDIENT":
                    editIngredient();
                    break;
                case "REMOVE_PRODUCT_MANAGER":
                    removeProductManager();
                    break;
                case "ADD_INGREDIENT":
                    addIngredient();
                    break;
                case "ADD_DISH":
                    addDish();
                    break;
                case "ADD_FOODPACKAGE":
                    addFoodPackage();
                    break;
                case "Reports" :
                    new WIndowReport(view,model.getHistorial());
                    break;
                case "AddProduct" :
                    addProductToSell();
                    break;
                case "addProductToDelivery" :
                    addProductToDelivery();
                    break;
                default:
                    break;
            }
            model.writeData();
        } catch (ProductNotFoundException | ConsumableTypeNotFound ex) {
            view.showError(ex.getMessage());

        }
    }

    private void sellDelivery() {
        try {
            if (viewDelivery.validateFields()){
                ArrayList<String> ids = viewDelivery.getLisProducts();
                ArrayList<Consumable> products = new ArrayList<>();
                for (String consumable : ids)
                    products.add(model.searchProductAll(consumable));
                model.sellProducts(Restaurant.createClient(viewDelivery.getDocument(), products,viewDelivery.getAddress()), viewDelivery.getTotalPrice());
                viewDelivery.close();
                view.showSuccesfull();
            }
        } catch (EmptyFieldsException | ProductNotFoundException e) {
            view.showError(e.getMessage());
        }
    }

    private void sell() {
        try {
            if (viewSell.validateFields()){
                ArrayList<String> ids = viewSell.getLisProducts();
                ArrayList<Consumable> products = new ArrayList<>();
                for (String consumable : ids)
                    products.add(model.searchProductAll(consumable));
                model.sellProducts(Restaurant.createClient(viewSell.getDocument(), products), viewSell.getTotalPrice());
                viewSell.close();
                view.showSuccesfull();
            }
        } catch (EmptyFieldsException | ProductNotFoundException e) {
            view.showError(e.getMessage());
        }
    }

    private void removeProductManager() {
        try {
            model.removeConsumable(viewManager.getIdFromProductToEdit());
            viewManager.updateTableProducts(model.getListProbablyResultsAllProducts(""));
        } catch (NotSelectedCellException e) {
            view.showError(e.getMessage());

        }
    }

    private void showWindowManager() {
        viewManager.clear();
        viewManager.setDatainTable(model.getAllProducts());
        viewManager.setVisible(true);
    }

    private void showEditManagerConsumable() {
        try {
            String id = viewManager.getIdFromProductToEdit();
            if (id.toLowerCase().contains("d")) {
                viewEditDish = new WindowAddDish(this, this, true, viewManager);
                viewEditDish.setData(model.searchProductAll(id));
                viewEditDish.setVisible(true);
            } else if (id.toLowerCase().contains("i")) {
                viewEditIngredients = new WindowAddIngredient(this, this, true, viewManager);
                viewEditIngredients.setData(model.searchProductAll(id));
                viewEditIngredients.setVisible(true);

            } else if (id.toLowerCase().contains("p")) {

                viewEditFoodPackage = new WindowAddFoodPackage(this, this, true, viewManager);
                viewEditFoodPackage.setData(model.searchProductAll(id));
                viewEditFoodPackage.setVisible(true);

            }
        } catch (ProductNotFoundException | NotSelectedCellException e) {
            view.showError(e.getMessage());

        }
    }

    private void addProductManager() {
        int option = viewManager.showSelectedWindow();
        if (option == 0) {
            viewAddIngredient.setVisible(true);
        } else if (option == 1) {
            viewAddFoodPackage.setVisible(true);
        } else if (option == 2) {
            viewAddDish.putItemAtStart(model.getListProbablyResultsIngredients(""));
            viewAddDish.setVisible(true);
        }
    }

    private void showWindowDelivery() {
        viewDelivery.putItemAtStart(model.getListProbablyResults(""));
        viewDelivery.setVisible(true);
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        try {
            switch (e.getComponent().getName()) {
                case "CMB_LIST_PRODUCTS":
                    updateListProbablyProducts(e);
                    break;
                case "TABLE_PRODUCTS":
                    if (e.getKeyChar() == 8)
                        viewSell.removeRowSelected();
                    break;
                case "TABLE_PRODUCTS_DELIVERY":
                    if (e.getKeyChar() == 8)
                        viewDelivery.removeRowSelected();
                    break;
                case "CMB_LIST_PRODUCTS_DELIVERY":
                    updateListProbablyProductsDelivery(e);
                    break;
                case "TXT_SEARCH_MANAGER":
                    updateTableProbablyProducts(e);
                    break;
                case "CMB_LIST_PRODUCTS_MANAGER_EDIT":
                    updateListProbablyProductsManagerEdit(e);
                    break;
                case "CMB_LIST_PRODUCTS_MANAGER_ADD":
                    updateListProbablyProductsManagerAdd(e);
                    break;
            }
        } catch (NullPointerException ex) {
            view.showError(ex.getMessage());

        }
    }

    private void updateListProbablyProductsManagerEdit(KeyEvent e) {
        if ((e.getKeyChar() == 8) || (e.getKeyChar() >= 44 && e.getKeyChar() <= 57) || (e.getKeyChar() >= 65 && e.getKeyChar() <= 122))
            viewEditDish.updateListProducts(model.getListProbablyResultsIngredients(viewEditDish.getListProductText()));
        else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                if (viewEditDish.getSelectedItem() != null) {
                    viewEditDish.setNewProduct_inTable(model.searchIngredient(viewEditDish.getSelectedItem().getId()));
                }
            } catch (ProductNotFoundException ex) {
                view.showError(ex.getMessage());

            }
        }
    }

    private void updateListProbablyProductsManagerAdd(KeyEvent e) {
        if ((e.getKeyChar() == 8) || (e.getKeyChar() >= 44 && e.getKeyChar() <= 57) || (e.getKeyChar() >= 65 && e.getKeyChar() <= 122))
            viewAddDish.updateListProducts(model.getListProbablyResultsIngredients(viewAddDish.getListProductText()));
        else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                if (viewAddDish.getSelectedItem() != null) {
                    viewAddDish.setNewProduct_inTable(model.searchIngredient(viewAddDish.getSelectedItem().getId()));
                }
            } catch (ProductNotFoundException ex) {
                view.showError(ex.getMessage());

            }
        }
    }

    private void addProductToSell(){
        if (viewSell.getProductSelected() != null) {
            try {
                viewSell.setNewProduct_inTable(model.searchProduct(viewSell.getProductSelected().getId()));
            } catch (ProductNotFoundException e) {
                view.showError(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void addProductToDelivery(){
        if (viewDelivery.getProductSelected() != null) {
            try {
                viewDelivery.setNewProduct_inTable(model.searchProduct(viewDelivery.getProductSelected().getId()));
            } catch (ProductNotFoundException e) {
                view.showError(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void updateTableProbablyProducts(KeyEvent e) {
        if ((e.getKeyChar() == 8) || (e.getKeyChar() >= 44 && e.getKeyChar() <= 57) || (e.getKeyChar() >= 65 && e.getKeyChar() <= 122))
            viewManager.updateTableProducts(model.getListProbablyResultsAllProducts(viewManager.getTextToSearch()));

    }

    private void updateListProbablyProductsDelivery(KeyEvent e) {
        if ((e.getKeyChar() == 8) || (e.getKeyChar() >= 44 && e.getKeyChar() <= 57) || (e.getKeyChar() >= 65 && e.getKeyChar() <= 122))
            viewDelivery.updateListProducts(model.getListProbablyResults(viewDelivery.getListProductText()));
        else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                if (viewDelivery.getProductSelected() != null) {
                    viewDelivery.setNewProduct_inTable(model.searchProduct(viewDelivery.getProductSelected().getId()));
                }
            } catch (ProductNotFoundException ex) {
                view.showError(ex.getMessage());

            }
        }
    }

    private void updateListProbablyProducts(KeyEvent e) {
        if ((e.getKeyChar() == 8) || (e.getKeyChar() >= 44 && e.getKeyChar() <= 57) || (e.getKeyChar() >= 65 && e.getKeyChar() <= 122))
            viewSell.updateListProducts(model.getListProbablyResults(viewSell.getListProductText()));
        else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                if (viewSell.getProductSelected() != null) {
                    viewSell.setNewProduct_inTable(model.searchProduct(viewSell.getProductSelected().getId()));
                }
            } catch (ProductNotFoundException ex) {
                view.showError(ex.getMessage());

            }
        }
    }

    private void showWindowSellProduct() {
        viewSell.putDataOnStart(model.getListProbablyResults(""));
        viewSell.setVisible(true);
    }

    private void editDish() throws ProductNotFoundException, ConsumableTypeNotFound {
        ArrayList<String> dataIngredients = viewEditDish.getIngredients();
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for (String dataIngredient : dataIngredients) {
            ingredients.add(model.searchIngredient(dataIngredient));
        }
        String name = viewEditDish.getNameIng();
        if (viewEditDish.isFillAlCamps()) {
            try {
                model.editConsumable(Restaurant.createConsumable(viewManager.getIdFromProductToEdit(), name, ingredients.get(0).getExpirationDate(), 0, 0, ingredients));
                viewEditDish.clear();
            } catch (NotSelectedCellException e) {
                view.showError(e.getMessage());
            }
        } else
            try {
                throw new EmptyFieldsException();
            } catch (EmptyFieldsException e) {
                view.showError(e.getMessage());
            }
        viewManager.updateTableProducts(model.getListProbablyResultsAllProducts(""));
    }

    private void editIngredient() throws ConsumableTypeNotFound, ProductNotFoundException {
        try {
            model.editConsumable(Restaurant.createConsumable(viewManager.getIdFromProductToEdit(), viewEditIngredients.getName(), viewEditIngredients.getLocalDate(), viewEditIngredients.getTxtPrice(), viewEditIngredients.getTxtQuantity(), null));
            viewEditIngredients.dispose();
            viewManager.updateTableProducts(model.getListProbablyResultsAllProducts(""));
            viewEditIngredients.clear();
        } catch (NotSelectedCellException e) {
            view.showError(e.getMessage());

        }
    }

    private void editFoodPackage() throws ConsumableTypeNotFound, ProductNotFoundException {
        try {
            model.editConsumable(Restaurant.createConsumable(viewManager.getIdFromProductToEdit(), viewEditFoodPackage.getName(), viewEditFoodPackage.getLocalDate(), viewEditFoodPackage.getTxtPrice(), viewEditFoodPackage.getTxtQuantity(), null));
            viewEditFoodPackage.dispose();
            viewManager.updateTableProducts(model.getListProbablyResultsAllProducts(""));
            viewEditFoodPackage.clear();
        } catch (NotSelectedCellException e) {
            view.showError(e.getMessage());
        }
    }

    private void addIngredient() throws ConsumableTypeNotFound {
        try {
            if (!viewAddIngredient.isEmpty()){
                model.addConsumable(Restaurant.createConsumable(model.generateIdIngredient(), viewAddIngredient.getName(), viewAddIngredient.getLocalDate(), viewAddIngredient.getTxtPrice(), viewAddIngredient.getTxtQuantity(), null));
            viewManager.updateTableProducts(model.getListProbablyResultsAllProducts(""));
            viewAddIngredient.clear();
            }
        } catch (EmptyFieldsException e) {
            view.showError(e.getMessage());
        }

    }

    private void addFoodPackage() throws ConsumableTypeNotFound {
        try {
            if (!viewAddFoodPackage.isEmpty()) {
                model.addConsumable(Restaurant.createConsumable(model.generateIdFoodPackage(), viewAddFoodPackage.getName(), viewAddFoodPackage.getLocalDate(), viewAddFoodPackage.getTxtPrice(), viewAddFoodPackage.getTxtQuantity(), null));
                viewManager.updateTableProducts(model.getListProbablyResultsAllProducts(""));
                viewAddFoodPackage.clear();
            }
        } catch (EmptyFieldsException e) {
            view.showError(e.getMessage());
        }

    }

    private void addDish() throws ConsumableTypeNotFound, ProductNotFoundException {
        viewAddDish.putItemAtStart(model.getListProbablyResultsIngredients(""));
        try {
            if (viewAddDish.isFillAlCamps()) {
                ArrayList<String> dataIngredients = viewAddDish.getIngredients();
                ArrayList<Ingredient> ingredients = new ArrayList<>();
                for (String dataIngredient : dataIngredients) {
                    ingredients.add(model.searchIngredient(dataIngredient));
                }
                model.addConsumable(Restaurant.createConsumable(model.generateIdDish(), viewAddDish.getNameIng(), ingredients.get(0).getExpirationDate(), 0, 1, ingredients));
                viewManager.updateTableProducts(model.getListProbablyResultsAllProducts(""));
                viewAddDish.clear();
            } else
                throw new EmptyFieldsException();
        } catch (EmptyFieldsException e) {
            view.showError(e.getMessage());
        }
    }


}