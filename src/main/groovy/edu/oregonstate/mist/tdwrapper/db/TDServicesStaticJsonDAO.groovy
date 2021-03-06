package edu.oregonstate.mist.tdwrapper.db

import edu.oregonstate.mist.api.jsonapi.ResourceObject
import groovy.json.JsonSlurper

class TDServicesStaticJsonDAO {

    private File servicesFile

    TDServicesStaticJsonDAO(String sourceJsonFile) {
        servicesFile = new File(sourceJsonFile)
    }

    /**
     * Get all services
     * @return
     */
    public List<ResourceObject> getTDServices(String baseUri) {
        def servicesRaw = getJsonRaw()
        servicesRaw.collect { id, service -> getResourceObject(id, service, baseUri) }
    }

    /**
     * Get service by ID
     * @param id
     * @return
     */
    public ResourceObject getTDServiceByID(String id, String baseUri) {
        def servicesRaw = getJsonRaw()

        if (!servicesRaw[id]) {
            return null
        }

        getResourceObject(id, servicesRaw[id], baseUri)
    }

    /**
     * Helper method to create resource object
     * @param serviceRaw
     * @return
     */
    private ResourceObject getResourceObject(String id, def serviceRaw, String baseUri) {
        new ResourceObject(
            id: id,
            type: "service",
            attributes: serviceRaw,
            links: ['self': baseUri + id]
        )
    }

    /**
     * Helper method to slurp json file into a def
     * @return
     */
    private def getJsonRaw() {
        JsonSlurper jsonSlurper = new JsonSlurper()
        jsonSlurper.parseText(servicesFile.getText())
    }
}
