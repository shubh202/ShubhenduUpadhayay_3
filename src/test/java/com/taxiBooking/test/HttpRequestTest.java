package com.taxiBooking.test;

import com.taxiBooking.service.serviceController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(serviceController.class)
public class HttpRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private serviceController service;

    /* Test 1: Check is requested before reset
    *  Expected: 400 Bad_Request
    *  Actual: 400 Bad_Request
    */
    @Test
    public void testEndpoint_check() throws Exception {
        ResponseEntity response = new ResponseEntity(HttpStatus.BAD_REQUEST);
        when(service.check()).thenReturn(response);
        this.mockMvc.perform(get("/api/check")).andExpect(status().isBadRequest());
    }

    /* Test 2: Tick is requested before reset
    *  Expected: 200 OK
    *  Actual: 200 OK
    */
    @Test
    public void testEndpoint_tick() throws Exception {
        ResponseEntity response = new ResponseEntity(HttpStatus.OK);
        when(service.check()).thenReturn(response);
        this.mockMvc.perform(put("/api/tick")).andExpect(status().isOk());
    }

    /* Test 3: Book is requested before reset
    *  Expected: 400 Bad_Request
    *  Actual: 400 Bad_Request
    */
    @Test
    public void testEndpoint_book() throws Exception {
        ResponseEntity response = new ResponseEntity(HttpStatus.BAD_REQUEST);
        when(service.check()).thenReturn(response);
        mockMvc.perform(
                put("/api/book/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"source\" :{ \"x\" :4, \"y\" :4}, \"destination\": { \"x\":6, \"y\":6} }"));
        this.mockMvc.perform(get("/api/check")).andExpect(status().isBadRequest());
    }

    /* Test 4: Reset is requested
    *  Expected: 200 OK
    *  Actual: 200 OK
    */

    @Test
    public void testEndpoint_reset() throws Exception {
        ResponseEntity response = new ResponseEntity(HttpStatus.OK);
        when(service.reset()).thenReturn(response);
        this.mockMvc.perform(put("/api/reset")).andExpect(status().isOk());
    }

    /* Test 5: Check is requested after reset
    *  Expected: 200 OK
    *  Actual: 200 OK
    */
    @Test
    public void testEndpoint_reset_2() throws Exception {
        ResponseEntity response = new ResponseEntity(HttpStatus.OK);
        when(service.reset()).thenReturn(response);
        this.mockMvc.perform(put("/api/reset")).andExpect(status().isOk());

        ResponseEntity response_Check = new ResponseEntity("Taxi:{id:1, taxiLocation:{x:0,y: 0} , cutomerLocation: {x:-1,y: -1}, destinationLocation: " +
                "{ x:-1 , y:-1}, booked:false, bookedTime:0}Taxi:{id:2, taxiLocation:{x:0,y: 0} , cutomerLocation: {x:-1,y: -1}, destinationLocation: { x:-1 , y:-1}, " +
                "booked:false, bookedTime:0}Taxi:{id:3, taxiLocation:{x:0,y: 0} , cutomerLocation: {x:-1,y: -1}, destinationLocation: { x:-1 , y:-1}, booked:false, " +
                "bookedTime:0}", HttpStatus.OK);
        when(service.check()).thenReturn(response_Check);
        this.mockMvc.perform(get("/api/check/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Taxi:{id:1, taxiLocation:{x:0,y: 0} , cutomerLocation: {x:-1,y: -1}, destinationLocation: " +
                        "{ x:-1 , y:-1}, booked:false, bookedTime:0}Taxi:{id:2, taxiLocation:{x:0,y: 0} , cutomerLocation: {x:-1,y: -1}, destinationLocation: { x:-1 , y:-1}, " +
                        "booked:false, bookedTime:0}Taxi:{id:3, taxiLocation:{x:0,y: 0} , cutomerLocation: {x:-1,y: -1}, " +
                        "destinationLocation: { x:-1 , y:-1}, booked:false, bookedTime:0}")));
    }

    /* Test 6: Tick is requested after reset
    *  Expected: 200 OK
    *  Actual: 200 OK
    */

    @Test
    public void testEndpoint_tick_2() throws Exception {
        ResponseEntity response = new ResponseEntity(HttpStatus.OK);
        when(service.tick()).thenReturn(response);
        this.mockMvc.perform(put("/api/tick")).andExpect(status().isOk());

    }

    /* Test 7: Book is requested
    *  Expected: 200 OK
    *  Actual: 200 OK
    */
    @Test
    public void testEndpoint_book_1() throws Exception {
        ResponseEntity response = new ResponseEntity(HttpStatus.OK);
        when(service.reset()).thenReturn(response);
        this.mockMvc.perform(put("/api/reset")).andExpect(status().isOk());


        ResponseEntity response_Check = new ResponseEntity("Taxi:{id:1, taxiLocation:{x:0,y: 0} , cutomerLocation: {x:4,y: 4}, destinationLocation: { x:6 , y:6}, booked:true, bookedTime:12}Taxi:{id:2, taxiLocation:{x:0,y: 0} , cutomerLocation: {x:-1,y: -1}, destinationLocation: { x:-1 , y:-1}, booked:false, bookedTime:0}Taxi:{id:3, taxiLocation:{x:0,y: 0} , cutomerLocation: {x:-1,y: -1}, destinationLocation: { x:-1 , y:-1}, booked:false, bookedTime:0}", HttpStatus.OK);

        mockMvc.perform(
                put("/api/book/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"source\" :{ \"x\" :4, \"y\" :4}, \"destination\": { \"x\":6, \"y\":6} }"));

        when(service.check()).thenReturn(response_Check);
        this.mockMvc.perform(get("/api/check/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Taxi:{id:1, taxiLocation:{x:0,y: 0} , cutomerLocation: {x:4,y: 4}, destinationLocation: { x:6 , y:6}, booked:true, bookedTime:12}Taxi:{id:2, taxiLocation:{x:0,y: 0} , cutomerLocation: {x:-1,y: -1}, destinationLocation: { x:-1 , y:-1}, booked:false, bookedTime:0}Taxi:{id:3, taxiLocation:{x:0,y: 0} , cutomerLocation: {x:-1,y: -1}, destinationLocation: { x:-1 , y:-1}, booked:false, bookedTime:0}")));
    }

    /* Test 8: Book is requested for car 2
    *  Expected: 200 OK
    *  Actual: 200 OK
    */
    @Test
    public void testEndpoint_book_2() throws Exception {
        ResponseEntity response = new ResponseEntity(HttpStatus.OK);
        when(service.reset()).thenReturn(response);
        this.mockMvc.perform(put("/api/reset")).andExpect(status().isOk());


        ResponseEntity response_Check = new ResponseEntity("Taxi:{id:1, taxiLocation:{x:0,y: 0} , cutomerLocation: {x:4,y: 4}, destinationLocation: { x:6 , y:6}, booked:true, bookedTime:12}Taxi:{id:2, taxiLocation:{x:0,y: 0} , cutomerLocation: {x:4,y: 4}, destinationLocation: { x:6 , y:6}, booked:true, bookedTime:12}Taxi:{id:3, taxiLocation:{x:0,y: 0} , cutomerLocation: {x:-1,y: -1}, destinationLocation: { x:-1 , y:-1}, booked:false, bookedTime:0}", HttpStatus.OK);

        mockMvc.perform(
                put("/api/book/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"source\" :{ \"x\" :4, \"y\" :4}, \"destination\": { \"x\":6, \"y\":6} }"));

        when(service.check()).thenReturn(response_Check);
        this.mockMvc.perform(get("/api/check/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Taxi:{id:1, taxiLocation:{x:0,y: 0} , cutomerLocation: {x:4,y: 4}, destinationLocation: { x:6 , y:6}, booked:true, bookedTime:12}Taxi:{id:2, taxiLocation:{x:0,y: 0} , cutomerLocation: {x:4,y: 4}, destinationLocation: { x:6 , y:6}, booked:true, bookedTime:12}Taxi:{id:3, taxiLocation:{x:0,y: 0} , cutomerLocation: {x:-1,y: -1}, destinationLocation: { x:-1 , y:-1}, booked:false, bookedTime:0}")));
    }

    /* Test 9: Book is requested car 3
    *  Expected: 200 OK
    *  Actual: 200 OK
    */
    @Test
    public void testEndpoint_book_3() throws Exception {
        ResponseEntity response = new ResponseEntity(HttpStatus.OK);
        when(service.reset()).thenReturn(response);
        this.mockMvc.perform(put("/api/reset")).andExpect(status().isOk());


        ResponseEntity response_Check = new ResponseEntity("Taxi:{id:1, taxiLocation:{x:0,y: 0} , cutomerLocation: {x:4,y: 4}, destinationLocation: { x:6 , y:6}, booked:true, bookedTime:12}Taxi:{id:2, taxiLocation:{x:0,y: 0} , cutomerLocation: {x:4,y: 4}, destinationLocation: { x:6 , y:6}, booked:true, bookedTime:12}Taxi:{id:3, taxiLocation:{x:0,y: 0} , cutomerLocation: {x:4,y: 4}, destinationLocation: { x:6 , y:6}, booked:true, bookedTime:12}", HttpStatus.OK);

        mockMvc.perform(
                put("/api/book/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"source\" :{ \"x\" :4, \"y\" :4}, \"destination\": { \"x\":6, \"y\":6} }"));

        when(service.check()).thenReturn(response_Check);
        this.mockMvc.perform(get("/api/check/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Taxi:{id:1, taxiLocation:{x:0,y: 0} , cutomerLocation: {x:4,y: 4}, destinationLocation: { x:6 , y:6}, booked:true, bookedTime:12}Taxi:{id:2, taxiLocation:{x:0,y: 0} , cutomerLocation: {x:4,y: 4}, destinationLocation: { x:6 , y:6}, booked:true, bookedTime:12}Taxi:{id:3, taxiLocation:{x:0,y: 0} , cutomerLocation: {x:4,y: 4}, destinationLocation: { x:6 , y:6}, booked:true, bookedTime:12}")));
    }

}