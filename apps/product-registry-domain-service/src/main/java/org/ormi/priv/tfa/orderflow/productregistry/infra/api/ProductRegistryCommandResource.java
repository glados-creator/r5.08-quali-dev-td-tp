package org.ormi.priv.tfa.orderflow.productregistry.infra.api;

import java.net.URI;
import java.util.UUID;

import org.jboss.resteasy.reactive.RestResponse;
import org.ormi.priv.tfa.orderflow.contracts.productregistry.v1.write.RegisterProductCommandDto;
import org.ormi.priv.tfa.orderflow.contracts.productregistry.v1.write.UpdateProductDescriptionParamsDto;
import org.ormi.priv.tfa.orderflow.contracts.productregistry.v1.write.UpdateProductNameParamsDto;
import org.ormi.priv.tfa.orderflow.kernel.product.ProductId;
import org.ormi.priv.tfa.orderflow.productregistry.application.ProductCommand.RetireProductCommand;
import org.ormi.priv.tfa.orderflow.productregistry.application.ProductCommand.UpdateProductDescriptionCommand;
import org.ormi.priv.tfa.orderflow.productregistry.application.ProductCommand.UpdateProductNameCommand;
import org.ormi.priv.tfa.orderflow.productregistry.application.RegisterProductService;
import org.ormi.priv.tfa.orderflow.productregistry.application.RetireProductService;
import org.ormi.priv.tfa.orderflow.productregistry.application.UpdateProductService;
import org.ormi.priv.tfa.orderflow.productregistry.infra.web.dto.CommandDtoMapper;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

/**
 * REST API for product commands.
 */
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductRegistryCommandResource {

    private final CommandDtoMapper mapper;
    private final RegisterProductService registerProductService;
    private final RetireProductService retireProductService;
    private final UpdateProductService updateProductService;

    /**
     * Constructor for ProductRegistryCommandResource
     * 
     * @param mapper CommandDtoMapper le mapper pour les DTO de commande
     * @param registerProductService RegisterProductService le service d'enregistrement de produit
     * @param retireProductService RetireProductService le service de retrait de produit
     * @param updateProductService UpdateProductService le service de mise à jour de produit
     */
    @Inject
    public ProductRegistryCommandResource(
            CommandDtoMapper mapper,
            RegisterProductService registerProductService,
            RetireProductService retireProductService,
            UpdateProductService updateProductService) {
        this.mapper = mapper;
        this.registerProductService = registerProductService;
        this.retireProductService = retireProductService;
        this.updateProductService = updateProductService;
    }

    /**
     * Registers a new product
     * 
     * @param cmd RegisterProductCommandDto les données de la commande d'enregistrement
     * @param uriInfo UriInfo les informations d'URI de la requête
     * @return RestResponse<Void> la réponse REST avec le statut 201 Created
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResponse<Void> registerProduct(RegisterProductCommandDto cmd, @Context UriInfo uriInfo) {
        final ProductId productId = registerProductService.handle(mapper.toCommand(cmd));
        return RestResponse.created(
                URI.create(uriInfo.getAbsolutePathBuilder().path("/products/" + productId.value()).build().toString()));
    }

    /**
     * Retires an existing product
     * 
     * @param productId String l'identifiant du produit à retirer
     * @return RestResponse<Void> la réponse REST avec le statut 204 No Content
     */
    @DELETE
    @Path("/{id}")
    public RestResponse<Void> retireProduct(@PathParam("id") String productId) {
        retireProductService.retire(new RetireProductCommand(new ProductId(UUID.fromString(productId))));
        return RestResponse.noContent();
    }

    /**
     * Updates a product's name
     * 
     * @param productId String l'identifiant du produit à mettre à jour
     * @param params UpdateProductNameParamsDto les paramètres de mise à jour du nom
     * @return RestResponse<Void> la réponse REST avec le statut 204 No Content
     */
    @PATCH
    @Path("/{id}/name")
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResponse<Void> updateProductName(@PathParam("id") String productId, UpdateProductNameParamsDto params) {
        updateProductService
                .handle(new UpdateProductNameCommand(new ProductId(UUID.fromString(productId)), params.name()));
        return RestResponse.noContent();
    }

    /**
     * Updates a product's description
     * 
     * @param productId String l'identifiant du produit à mettre à jour
     * @param params UpdateProductDescriptionParamsDto les paramètres de mise à jour de la description
     * @return RestResponse<Void> la réponse REST avec le statut 204 No Content
     */
    @PATCH
    @Path("/{id}/description")
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResponse<Void> updateProductDescription(@PathParam("id") String productId,
            UpdateProductDescriptionParamsDto params) {
        updateProductService.handle(new UpdateProductDescriptionCommand(new ProductId(UUID.fromString(productId)),
                params.description()));
        return RestResponse.noContent();
    }
}