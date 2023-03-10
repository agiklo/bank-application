package pl.matcodem.accountcmd.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.matcodem.accountcmd.commands.UpdateUserCommand;
import pl.matcodem.accountcore.dto.BaseResponse;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;
import static pl.matcodem.accountcore.configuration.EndpointConstants.*;

@Slf4j
@RestController
@RequestMapping(path = USERS_ENDPOINT_V1)
@RequiredArgsConstructor
public class UpdateUserController {

    private final CommandGateway commandGateway;

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<BaseResponse> updateUser(@PathVariable("id") String id,
                                                   @Valid @RequestBody UpdateUserCommand command) {
        try {
            command.setId(id);
            commandGateway.send(command);
            var message = "User successfully updated!";
            return new ResponseEntity<>(new BaseResponse(message), OK);
        } catch (Exception e) {
            log.error(e.toString(), e);
            var safeErrorMessage = "Error while processing update user request for id - " + id;
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), INTERNAL_SERVER_ERROR);
        }
    }
}
