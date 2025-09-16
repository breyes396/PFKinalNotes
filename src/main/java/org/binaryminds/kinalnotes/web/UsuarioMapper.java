package org.binaryminds.kinalnotes.web;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


//@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(source = "name" ,target = "nombre")
    @Mapping(source = "password ", target = "contraseña")
    @Mapping(source = "mail", target ="correo" )
    @Mapping(source = "role" , target = "rol")
}



//@Mapper(componentModel = "spring", uses ={GenreMapper.class})
//public interface PeliculaMapper {

//
//    //firmas de metodos : toDto -> convertir a DTO
//    @Mapping(source = "nombre",target = "name")
//    @Mapping(source = "duracion",target = "duration")
//    @Mapping(source = "genero",target = "genre",qualifiedByName ="generarGenre" )
//    @Mapping(source = "fechaEstreno",target = "releaseDate")
//    @Mapping(source = "calificacion",target = "rating")
//
//    public PeliculaDto toDto(PeliculaEntity Entity);
//
//
//    PeliculaDto toDto(Optional<PeliculaEntity> entity);
//
//    public List<PeliculaDto> toDto(Iterable<PeliculaEntity> entities);
//
//
//
//    /// para convertir Dto a Entity 'toEntity
//
//    @InheritInverseConfiguration
//    @Mapping(source = "genre",target = "genero",qualifiedByName = "generarGenero")
//    PeliculaEntity toEntity(PeliculaDto peliculaDto);
//
//    // auto actualizar el ModPeliculaDto a Pelicula entity
//    @Mapping(source = "name" ,target = "nombre")
//    @Mapping(source = "releaseDate",target = "fechaEstreno")
//    @Mapping(source = "rating",target = "calificacion")
//    void modificarEntityFromDto(ModPeliculaDto modPeliculaDto ,@MappingTarget PeliculaEntity peliculaEntity);
//
//




