package com.hch.demo.controller.api.v1;

import com.hch.demo.enums.KeyEnum;
import com.hch.demo.enums.MsgEnum;
import com.hch.demo.enums.ResCode;
import com.hch.demo.model.entity.Store;
import com.hch.demo.model.value.StoreValue;
import com.hch.demo.service.StoreService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Tag(name="store", description = "지점 API")
@RequiredArgsConstructor
@RequestMapping(value="${demo.api}/stores", produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
public class StoreController {

    private final StoreService storeService;

    /**
     * id로 store 정보 조회
     * 도메인 클래스 컨버터 기능으로 조회
     * @param store
     * @return
     */
    @GetMapping("/{id}")
    public Map<String, Object> findById(@PathVariable("id") Store store) {

        Map<String, Object> response = new HashMap<>();

        if(store != null) {
            response.put(KeyEnum.RES_RESULT.getKey(), ResCode.SUCCESS.getMsg());
            response.put("store", store);

        } else {
            response.put(KeyEnum.RES_RESULT.getKey(), ResCode.FAIL.getMsg());
            response.put(KeyEnum.RES_MSG.getKey(), MsgEnum.MSG_USER_NOT_FOUND);
        }

        return response;
    }

    /**
     * 스토어정보 저장
     * @param value
     * @return
     */
    @PostMapping("")
    public Map<String, Object> save(@RequestBody StoreValue value) {

        log.info("StoreController >> save()");

        Map<String, Object> response = new HashMap<>();

        Store store = storeService.save(value);
        if(store != null) {
            response.put(KeyEnum.RES_RESULT.getKey(), ResCode.SUCCESS.getMsg());
            response.put("store", store);
        } else {
            response.put(KeyEnum.RES_RESULT.getKey(), ResCode.FAIL.getMsg());
            response.put(KeyEnum.RES_MSG.getKey(), MsgEnum.MSG_USER_JOIN_FAIL);
        }

        return response;
    }

}
