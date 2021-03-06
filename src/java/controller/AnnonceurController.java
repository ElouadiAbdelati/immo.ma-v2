package controller;

import bean.Annonce;
import bean.Annonceur;
import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import service.AnnonceurFacade;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import service.AuthUser;

@Named("annonceurController")
@SessionScoped
public class AnnonceurController implements Serializable {

    private Annonceur current = null;
    private DataModel items = null;
    @EJB
    private service.AnnonceurFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private  Annonceur connctedUser = null;
    @EJB
    private AuthUser authUser;

    public AuthUser getAuthUser() {
        return authUser;
    }

    public void setAuthUser(AuthUser authUser) {
        this.authUser = authUser;
    }

    public boolean isConnected() {
        return authUser.getCurUser() != null;
    }

    public String signIn() {
        System.out.println("signIn " + getCurrent().toString());
        int res = ejbFacade.seConnecter(getCurrent().getEmail(), getCurrent().getPassword());
        if (res > 0) {
            authUser.signIn(getCurrent());
            return "/index?faces-redirect=true";
        } else if (res == -1) {
            JsfUtil.addErrorMessage("Login innexistant");
        } else if (res == -2) {
            JsfUtil.addErrorMessage("Password Incorrect innexistant");
        }
        current = new Annonceur();
        return null;
    }

    public Annonceur getConnctedUser() {

        if (connctedUser == null) {
            connctedUser = ejbFacade.findBylogin(getAuthUser()
                    .getCurUser()
                    .getEmail());
            return  connctedUser;
        }

        return connctedUser;
    }

    public String signUp() {
        System.out.println("com.immo.controller.UserController.signUp()" + getCurrent().getEmail());
        System.out.println("com.fst.controler.UserController.signUp()");
        int res = ejbFacade.seEnregister(getCurrent());
        System.out.println("com.fst.controler.UserController.signUp() : " + res);
        if (res > 0) {
            authUser.setCurUser(getCurrent());
            return "/index?faces-redirect=true";
        } else if (res == -1) {
            JsfUtil.addErrorMessage("User not registred");
        }
        current = new Annonceur();
        return null;
    }

    public String signOut() {
        authUser.signOut();
        return "/index?faces-redirect=true";

    }

    public AnnonceurController() {
    }

    public Annonceur getSelected() {
        if (current == null) {
            current = new Annonceur();
            selectedItemIndex = -1;
        }
        return current;
    }

    private AnnonceurFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Annonceur) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Annonceur();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AnnonceurCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Annonceur) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AnnonceurUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Annonceur) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AnnonceurDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Annonceur getAnnonceur(java.lang.Long id) {
        return ejbFacade.find(id);

    }

    @FacesConverter(forClass = Annonceur.class)
    public static class AnnonceurControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AnnonceurController controller = (AnnonceurController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "annonceurController");
            return controller.getAnnonceur(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Annonceur) {
                Annonceur o = (Annonceur) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Annonceur.class.getName());
            }
        }

    }
   
    public String edit(){
        System.out.println("edit annonceur " + connctedUser.toString());
        try {
            getFacade().edit(connctedUser);
        } catch (Exception e) {
        }
        return "/my_properties?faces-redirect=true";
    }
    public Annonceur getCurrent() {
        if (current == null) {
            System.out.println(" new Annonceur()");
            current = new Annonceur();
            return current;
        }
        return current;
    }

}
