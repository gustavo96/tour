package com.acme.tour.controller

import com.acme.tour.exception.PromocaoNotFoundException
import com.acme.tour.model.ErrorMessage
import com.acme.tour.model.Promocao
import com.acme.tour.model.ResponseJSON
import com.acme.tour.service.PromocaoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@RestController
@RequestMapping(value = ["/promocoes"])
class PromocaoController {
    @Autowired
    lateinit var promocaoService: PromocaoService

    // Exemplo usando Controller Advice
//    @GetMapping("/{id}")
//    fun getId(@PathVariable id:Long): ResponseEntity<Promocao>{
//        var promocao = promocaoService.getById(id) ?:
//            throw PromocaoNotFoundException("promocao ${id} nao localizado")
//        return ResponseEntity(promocao,HttpStatus.OK)
//    }

    // Modelo mais usado para excecoes de negócio
    @GetMapping("/{id}")
    fun getId(@PathVariable id:Long): ResponseEntity<Any>{
        var promocao = promocaoService.getById(id)
        return if (promocao != null)
            ResponseEntity(promocao,HttpStatus.OK)
        else
            ResponseEntity(ErrorMessage("Promocao nao localizada","Promocao ${id} nao encontrada"),HttpStatus.NOT_FOUND)
    }
    @PostMapping()
    fun create(@RequestBody promocao: Promocao): ResponseEntity<ResponseJSON>{
        promocaoService.create(promocao)
        val responseJSON = ResponseJSON("OK", Date())
        return ResponseEntity(responseJSON,HttpStatus.CREATED)
    }
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit>{
        var status = HttpStatus.NOT_FOUND
        if (promocaoService.getById(id) != null){
            status = HttpStatus.ACCEPTED
            promocaoService.delete(id)
        }
        return ResponseEntity(Unit,status)
    }
    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody promocao: Promocao): ResponseEntity<Unit>{
        var status = HttpStatus.NOT_FOUND
        if (promocaoService.getById(id) != null){
            status = HttpStatus.ACCEPTED
            promocaoService.update(id,promocao)
        }
        return ResponseEntity(Unit,status)
    }
    @GetMapping()
    fun getAll(@RequestParam(required = false, defaultValue = "0") start: Int,
                @RequestParam(required = false, defaultValue = "3") size: Int): ResponseEntity<List<Promocao>> {
        val listaPromocoes = this.promocaoService.getAll(start,size)
        val status = if (listaPromocoes.size == 0) HttpStatus.NOT_FOUND else HttpStatus.OK
        return ResponseEntity(listaPromocoes,status)
    }

    @GetMapping("/ordenados")
    fun ordenado() = this.promocaoService.getAllSortedByLocal()

    @GetMapping("/count")
    fun count(): ResponseEntity<Map<String,Long>> =
         ResponseEntity.ok().body(mapOf("count" to this.promocaoService.count()))

}