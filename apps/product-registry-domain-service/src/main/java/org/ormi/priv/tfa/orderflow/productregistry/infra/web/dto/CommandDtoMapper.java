package org.ormi.priv.tfa.orderflow.productregistry.infra.web.dto;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.ormi.priv.tfa.orderflow.productregistry.application.ProductCommand.RegisterProductCommand;
import org.ormi.priv.tfa.orderflow.contracts.productregistry.v1.write.RegisterProductCommandDto;
import org.ormi.priv.tfa.orderflow.kernel.product.SkuIdMapper;

/**
 * Mapper for converting between DTOs and command objects
 */
@Mapper(
    componentModel = "cdi",
    builder = @Builder(disableBuilder = true),
    uses = { SkuIdMapper.class },
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CommandDtoMapper {
    
    /**
     * Converts a DTO to a command object
     * 
     * @param dto RegisterProductCommandDto le DTO de la commande d'enregistrement
     * @return RegisterProductCommand la commande d'enregistrement
     */
    public RegisterProductCommand toCommand(RegisterProductCommandDto dto);
    
    /**
     * Converts a command object to a DTO
     * 
     * @param command RegisterProductCommand la commande d'enregistrement
     * @return RegisterProductCommandDto le DTO de la commande d'enregistrement
     */
    public RegisterProductCommandDto toDto(RegisterProductCommand command);
}