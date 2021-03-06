package org.activiti.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wf.dp.dniprorada.base.dao.GenericEntityDao;
import org.wf.dp.dniprorada.dao.PlaceDao;
import org.wf.dp.dniprorada.dao.place.PlaceHierarchyRecord;
import org.wf.dp.dniprorada.dao.place.PlaceHierarchyTree;
import org.wf.dp.dniprorada.model.Place;
import org.wf.dp.dniprorada.model.PlaceType;

/**
 * @author dgroup
 * @since  20.07.2015
 */
@Controller
public class PlaceController {
    private static final Logger LOG = LoggerFactory.getLogger(PlaceController.class);

    private static final String JSON_TYPE = "Accept=application/json";

    @Autowired
    private PlaceDao placeDao;

    @Autowired
    @Qualifier("placeTypeDao")
    private GenericEntityDao<PlaceType> placeTypeDao;


    @RequestMapping(value   = "/getPlacesTree",
                    method  = RequestMethod.GET, headers = { JSON_TYPE })
    public  @ResponseBody PlaceHierarchyTree getPlacesTree (
            @RequestParam(value = "nID",            required = false)   Long    placeId,
            @RequestParam(value = "sID_UA",         required = false)   String  uaId,
            @RequestParam(value = "nID_PlaceType",  required = false)   Long    typeId,
            @RequestParam(value = "bArea",          required = false)   Boolean area,
            @RequestParam(value = "bRoot",          required = false)   Boolean root,
            @RequestParam(value = "nDeep",          defaultValue = "1") Long    deep
    ) {
        PlaceHierarchyRecord rootRecord = new PlaceHierarchyRecord();
        rootRecord.setPlaceId(placeId);
        rootRecord.setTypeId(typeId);
        rootRecord.setUaID(uaId);
        rootRecord.setArea(area);
        rootRecord.setRoot(root);
        rootRecord.setDeep(deep);
        return placeDao.getTreeDown(rootRecord);
    }


    @RequestMapping(value   = "/getPlace",
                    method  = RequestMethod.GET, headers = { JSON_TYPE })
    public  @ResponseBody PlaceHierarchyTree getPlace(
            @RequestParam(value = "nID",    required = false)       Long    placeId,
            @RequestParam(value = "sID_UA", required = false)       String  uaId,
            @RequestParam(value = "bTree",  defaultValue = "false") Boolean tree
    ) {
        return placeDao.getTreeUp(placeId, uaId, tree);
    }


    @RequestMapping(value   = "/setPlace",
                    method  = RequestMethod.POST, headers = { JSON_TYPE })
    public  @ResponseBody void setPlace(
            @RequestParam(value = "nID",            required = false) Long   placeId,
            @RequestParam(value = "sName",          required = false) String name,
            @RequestParam(value = "nID_PlaceType",  required = false) Long   typeId,
            @RequestParam(value = "sID_UA",         required = false) String uaId,
            @RequestParam(value = "sNameOriginal",  required = false) String originalName
    ) {
        // TODO need to understand which Dao should be used AbstractDaoXX or BaseXXX or GenericXX ...
        Place place = new Place();
        place.setId(placeId);
        place.setName(name);
        place.setPlaceTypeId(typeId);
        place.setUaId(uaId);
        place.setOriginalName(originalName);
        // placeTypeDao.saveOrUpdate( place ); TODO and now we can't do it because of GenericEntityDao<PlaceType>
        // Why we need a new DAO class which does not allow us to do basic operation (independent of type at all)?
    }


    @RequestMapping(value   = "/getPlaceType",
                    method  = RequestMethod.GET, headers = { JSON_TYPE })
    public  @ResponseBody PlaceType getPlaceType(
            @RequestParam(value = "nID") Long placeTypeId
    ) {
        return placeTypeDao.findByIdExpected(placeTypeId);
    }


    @RequestMapping(value   = "/setPlaceType",
                    method  = RequestMethod.POST, headers = { JSON_TYPE })
    public  @ResponseBody void setPlaceType(
            @RequestParam(value = "nID",    required = false)       Long    placeTypeId,
            @RequestParam(value = "sName",  required = false)       String  name,
            @RequestParam(value = "nOrder", required = false)       Long    order,
            @RequestParam(value = "bArea",  defaultValue = "false") Boolean area,
            @RequestParam(value = "bRoot",  defaultValue = "false") Boolean root
    ) {
        placeTypeDao.saveOrUpdate( new PlaceType(placeTypeId, name, order, area, root) );
    }

    @RequestMapping(value   = "/removePlaceType",
                    method  = RequestMethod.POST, headers = { JSON_TYPE })
    public  @ResponseBody void removePlaceType(
            @RequestParam(value = "nID") Long placeTypeId
    ) {
        placeTypeDao.delete(placeTypeId);
    }
}