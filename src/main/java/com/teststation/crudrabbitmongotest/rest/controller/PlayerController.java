package com.teststation.crudrabbitmongotest.rest.controller;

import com.teststation.crudrabbitmongotest.rest.dto.PlayerDto;
import com.teststation.crudrabbitmongotest.rest.dto.ResponseDto;
import com.teststation.crudrabbitmongotest.service.PlayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/player")
@Slf4j
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE
    }, consumes = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseDto savePlayer(@RequestBody PlayerDto player) {
        ResponseDto responseDto = new ResponseDto(ResponseDto.Status.SUCCESS, "Player saved to DB successfully");

        try {
            playerService.processPlayer(player);
        } catch (Exception ex) {
            log.error("Exception occurred during save method call, playerDto: {}", player);
            responseDto.setStatus(ResponseDto.Status.FAIL);
            responseDto.setMessage(ex.getMessage());
        }

        return responseDto;
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseDto<PlayerDto> getPlayers() {
        ResponseDto<PlayerDto> responseDto = new ResponseDto(ResponseDto.Status.SUCCESS, "Players successfully retrieved");
        responseDto.setData(playerService.getPlayers());
        return responseDto;
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseDto deletePlayer(@RequestParam String name) {
        return playerService.deletePlayer(name)
                ? new ResponseDto(ResponseDto.Status.SUCCESS, "Player successfully deleted")
                : new ResponseDto(ResponseDto.Status.FAIL, "Couldn't find player by provided name");
    }
}
