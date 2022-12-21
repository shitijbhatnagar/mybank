/*

Without providing the custom jdbc converters, an exception is raised
org.springframework.core.convert.ConverterNotFoundException: No converter found
capable of converting from type [java.sql.Timestamp] to type [java.time.ZonedDateTime]
*/

package com.sb.mybank.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;

//This configuration is required to facilitate conversion of Timestamp and ZonalDateTime for 'Timestamp' column
//https://docs.spring.io/spring-data/jdbc/docs/current/reference/html/#jdbc.custom-converters.writer
@Configuration
public class MyBankConfig extends AbstractJdbcConfiguration
{
    private static final ZoneId DEFAULT_ZONE = ZoneId.systemDefault();

    //Register the custom Jdbc Converter
    @Override
    public JdbcCustomConversions jdbcCustomConversions() {
        return new JdbcCustomConversions(Arrays.asList(
                TimestampToZonedDateTimeConverter.INSTANCE,
                ZonedDateTimeToTimestampConverter.INSTANCE));
    }

    //This convertor is used while reading the data from database to convert Timestamp to ZonalDateTime
    @ReadingConverter
    enum TimestampToZonedDateTimeConverter implements Converter<Timestamp, ZonedDateTime> {
        INSTANCE;

        @Override
        public ZonedDateTime convert(Timestamp source) {
            return source.toLocalDateTime().atZone(DEFAULT_ZONE);
        }
    }

    //This convertor is used while writing data from Java to database (H2 uses a timestamp type for date time values)
    @WritingConverter
    enum ZonedDateTimeToTimestampConverter implements Converter<ZonedDateTime, Timestamp> {
        INSTANCE;

        @Override
        public Timestamp convert(ZonedDateTime source) {
            return Timestamp.valueOf(source.withZoneSameInstant(DEFAULT_ZONE).toLocalDateTime());
        }
    }
}