package controller;

import bean.Annonce;
import bean.AnnonceStatus;
import bean.AnnonceType;
import bean.City;
import bean.helper.AnnonceTypeAnnonce;
import bean.helper.CategoryAnnonce;
import bean.helper.CityAnnonce;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import java.io.IOException;
import service.AnnonceFacade;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
import jms.producer.Producer;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import service.AnnonceTypeFacade;
import service.CategoryFacade;
import service.CityFacade;

@Named("annonceController")
@SessionScoped
public class AnnonceController implements Serializable {

    private Annonce current;
    private Annonce annonceDetail;
    private Long annonceDetailId;

    private DataModel items = null;

    private List<Annonce> annoncesLimit = null;

    int[] range = { 0, 5 };

    private List<Annonce> annonces = null;
    private List<CategoryAnnonce> categoryAnnonces = null;
    private List<AnnonceTypeAnnonce> annonceTypeAnnonces = null;
    private List<CityAnnonce> cityAnnonces = null;

    private List<City> citys = null;
    private List<AnnonceType> annonceTypes = null;
    private City citySearch;
    private AnnonceType annonceTypeSearch;
    private int nbrchambresSearch;
    private int tailleMinimaleSearch;
    private int tailleMaxSearch;
    private int nbrThermesSearch;

    @EJB
    private service.AnnonceFacade ejbFacade;

    @EJB
    private CategoryFacade categoryFacade;
    @EJB
    private AnnonceTypeFacade annonceTypeFacade;
    @EJB
    private CityFacade cityFacade;

    private PaginationHelper pagination;

    private int selectedItemIndex;
    
    
    private List<Boolean> picineSelectValues = Arrays.asList(true, false);
    private UploadedFile uploadedFile;

    public AnnonceController() {
    }

    public void init() {
        System.out.println("init !!!!!!");
        current = new Annonce();
        current.setActive(true);
        current.setStatus(AnnonceStatus.EN_COURS);
    }

    public void handleFileUpload(FileUploadEvent event) throws IOException {
        System.out.println("controller.AnnonceController.handleFileUpload()");
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "soulcloudname",
                "api_key", "516235241917812",
                "api_secret", "lZZVGEe68m3Y21EX_PnO8dtXdd8"));

        Map uploadResult = cloudinary.uploader().upload(event.getFile().getContents(), ObjectUtils.asMap("resource_type", "auto"));
        String url = uploadResult.get("url").toString();
        current.setImagePath(url);
    }

    public void showSelected() {
        System.out.println("controller.AnnonceController.showSelected()");
        System.out.println(current.toString());
    }

    public Annonce getSelected() {
        if (current == null) {
            current = new Annonce();
            selectedItemIndex = -1;
        }
        return current;
    }

    private AnnonceFacade getFacade() {
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
                    return new ListDataModel(getFacade()
                            .findRange(new int[] { getPageFirstItem(), getPageFirstItem() + getPageSize() }));
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
        current = (Annonce) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Annonce();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        System.out.println("dkhl l create");
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AnnonceCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Annonce) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AnnonceUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Annonce) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AnnonceDeleted"));
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
            current = getFacade().findRange(new int[] { selectedItemIndex, selectedItemIndex + 1 }).get(0);
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

    public Annonce getAnnonce(java.lang.Long id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Annonce.class)
    public static class AnnonceControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AnnonceController controller = (AnnonceController) facesContext.getApplication().getELResolver()
                    .getValue(facesContext.getELContext(), null, "annonceController");
            return controller.getAnnonce(getKey(value));
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
            if (object instanceof Annonce) {
                Annonce o = (Annonce) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName()
                        + "; expected type: " + Annonce.class.getName());
            }
        }

    }

    public List<Annonce> getAnnonces() {
        if (annonces == null) {
            annonces = getFacade().findAll();
        }
        System.out.println("controller.AnnonceController.getAnnonces() " + annonces.size());

        return annonces;
    } 
    public List<Annonce> getAnnoncesLimit() {
        if (annoncesLimit == null) {
            annoncesLimit = getFacade().findLastInserted(range);
        }
        return annoncesLimit;
    }

   

    public CategoryFacade getCategoryFacade() {
        return categoryFacade;
    }

    public List<CategoryAnnonce> getCategoryAnnonces() {

        if (categoryAnnonces == null) {
            categoryAnnonces = getCategoryFacade().coutAnnonceActiveByCategory();
        }
        return categoryAnnonces;
    }

    public AnnonceTypeFacade getAnnonceTypeFacade() {
        return annonceTypeFacade;
    }

    public List<AnnonceTypeAnnonce> getAnnonceTypeAnnonces() {
        if (annonceTypeAnnonces == null) {
            annonceTypeAnnonces = getAnnonceTypeFacade().countAnnonceActiveByType();
        }
        return annonceTypeAnnonces;
    }

    public List<CityAnnonce> getCityAnnonces() {
        if (cityAnnonces == null) {
            cityAnnonces = getCityFacade().countAnnonceActiveByCity();
        }
        return cityAnnonces;
    }

    public CityFacade getCityFacade() {
        return cityFacade;
    }

    public List<City> getCitys() {
        if (citys == null) {
            citys = getCityFacade().findAll();

        }
        return citys;
    }

    public List<AnnonceType> getAnnonceTypes() {
        if (annonceTypes == null) {
            annonceTypes = getAnnonceTypeFacade().findAll();
        }
        return annonceTypes;
    }

    public City getCitySearch() {
        return citySearch;
    }

    public void setCitySearch(City citySearch) {
        this.citySearch = citySearch;
    }

    public AnnonceType getAnnonceTypeSearch() {
        return annonceTypeSearch;
    }

    public void setAnnonceTypeSearch(AnnonceType annonceTypeSearch) {
        this.annonceTypeSearch = annonceTypeSearch;
    }

    public int getNbrchambresSearch() {
        return nbrchambresSearch;
    }

    public void setNbrchambresSearch(int nbrchambresSearch) {
        this.nbrchambresSearch = nbrchambresSearch;
    }

    public int getTailleMinimaleSearch() {
        return tailleMinimaleSearch;
    }

    public void setTailleMinimaleSearch(int tailleMinimaleSearch) {
        this.tailleMinimaleSearch = tailleMinimaleSearch;
    }

    public int getTailleMaxSearch() {
        return tailleMaxSearch;
    }

    public void setTailleMaxSearch(int tailleMaxSearch) {
        this.tailleMaxSearch = tailleMaxSearch;
    }

    public int getNbrThermesSearch() {
        return nbrThermesSearch;
    }

    public void setNbrThermesSearch(int nbrThermesSearch) {
        this.nbrThermesSearch = nbrThermesSearch;
    }

    public void search() {
        this.annonces = ejbFacade.search(citySearch, annonceTypeSearch, nbrchambresSearch, tailleMinimaleSearch, tailleMaxSearch, nbrThermesSearch);
        //Producer.sendMessage("Hello ....... ");
        
    }
    
    public String loadAnnonceDetail(){
        System.out.println("loadAnnonceDetail " +getAnnonceDetail().toString() + annonceDetailId );
        annonceDetail = getFacade()
                .find(annonceDetailId);
        if(annonceDetail==null) return "/index?faces-redirect=true";
        return "";
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public Annonce getCurrent() {
        return current;
    }

    public void setCurrent(Annonce current) {
        this.current = current;
    }

    public List<Boolean> getPicineSelectValues() {
        return picineSelectValues;
    }

    public void setPicineSelectValues(List<Boolean> picineSelectValues) {
        this.picineSelectValues = picineSelectValues;
    }

    public Annonce getAnnonceDetail() {
        if(annonceDetail==null) return  new Annonce();
        return annonceDetail;
    }

    public Long getAnnonceDetailId() {
        return annonceDetailId;
    }

    public void setAnnonceDetailId(Long annonceDetailId) {
        this.annonceDetailId = annonceDetailId;
    }
    
    
    
 
}
