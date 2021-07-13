package com.teststation.crudrabbitmongotest.config;

import com.teststation.crudrabbitmongotest.model.Player;
import com.teststation.crudrabbitmongotest.rest.dto.PlayerDto;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrikaConfig {

    @Bean
    public MapperFacade mapperFacade() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.getConverterFactory().registerConverter(new CustomConverter<PlayerDto, Player>() {
            @Override
            public Player convert(PlayerDto source, Type<? extends Player> destinationType, MappingContext mappingContext) {
                return new Player(source.getName(), source.getCountry(), source.getAge());
            }
        });

        mapperFactory.getConverterFactory().registerConverter(new CustomConverter<Player, PlayerDto>() {
            @Override
            public PlayerDto convert(Player source, Type<? extends PlayerDto> destinationType, MappingContext mappingContext) {
                return new PlayerDto(source.getName(), source.getCountry(), source.getAge());
            }
        });

        return mapperFactory.getMapperFacade();
    }
}
