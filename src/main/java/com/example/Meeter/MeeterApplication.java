package com.example.Meeter;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class MeeterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeeterApplication.class, args);
	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
		return builder -> {
			builder.simpleDateFormat("dd/MM/yyyy HH:mm:ss");
			builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
			builder.serializers(new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
			builder.deserializers(new LocalDateDeserializer(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			builder.deserializers(new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
			builder.deserializers(new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
		};
	}

}
