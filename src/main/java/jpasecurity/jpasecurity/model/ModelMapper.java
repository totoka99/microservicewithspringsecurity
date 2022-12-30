package jpasecurity.jpasecurity.model;

import jpasecurity.jpasecurity.model.dto.ExampleDto;
import jpasecurity.jpasecurity.model.dto.UserDto;
import jpasecurity.jpasecurity.model.entity.Example;
import jpasecurity.jpasecurity.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ModelMapper {
    ExampleDto toExampleDto(Example example);

    List<ExampleDto> toExampleDto(List<Example> example);

    UserDto toUserDto(User user);

    List<UserDto> toUserDto(List<User> user);
}
