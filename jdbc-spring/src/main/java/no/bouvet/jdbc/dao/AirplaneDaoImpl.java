package no.bouvet.jdbc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import no.bouvet.jdbc.domain.Airplane;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.incrementer.OracleSequenceMaxValueIncrementer;

public class AirplaneDaoImpl implements AirplaneDao {

	private OracleSequenceMaxValueIncrementer incrementor;
	private SimpleJdbcInsert insertAirplane;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public void save(Airplane airplane) {
		airplane.setId(incrementor.nextLongValue());
		SqlParameterSource parameters = new BeanPropertySqlParameterSource(airplane);
		insertAirplane.execute(parameters);
	}

	@Override
	public void update(Airplane airplane) {
		String query = "update airplane set name = :name where id = :airplaneId";
		SqlParameterSource parameters = new MapSqlParameterSource("name", airplane.getName()).addValue("airplaneId",
				airplane.getId());

		namedParameterJdbcTemplate.update(query, parameters);
	}

	@Override
	public void delete(Airplane airplane) {
		String query = "delete from airplane where id = :airplaneId";
		SqlParameterSource parameters = new MapSqlParameterSource("airplaneId", airplane.getId());

		namedParameterJdbcTemplate.update(query, parameters);
	}

	@Override
	public Airplane getById(Long airplaneId) {
		String query = "select * from airplane where id = :airplaneId";
		SqlParameterSource parameters = new MapSqlParameterSource("airplaneId", airplaneId);

		// Map using BeanPropertyRowMapper 
		return (Airplane) namedParameterJdbcTemplate.queryForObject(query, parameters,
				new BeanPropertyRowMapper<Airplane>(Airplane.class));
	}

	@Override
	public List<Airplane> getAllAirplanes() {
		String query = "select * from airplane";
		
		// Map using custom mapper
		return namedParameterJdbcTemplate.getJdbcOperations().query(query, new AirplaneMapper());
	}

	@Required
	public void setDataSource(DataSource dataSource) {
		this.insertAirplane = new SimpleJdbcInsert(dataSource).withTableName("airplane");
		this.incrementor = new OracleSequenceMaxValueIncrementer(dataSource,"HIBERNATE_SEQUENCE");
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	// Mapper between a database row and the domain object
	public static final class AirplaneMapper implements RowMapper<Airplane> {
		public Airplane mapRow(ResultSet rs, int rowNum) throws SQLException {
			Airplane airplane = new Airplane(rs.getString("name"));
			airplane.setId(rs.getLong("id"));
			airplane.setCreated(rs.getDate("created"));
			airplane.setCreatedBy(rs.getString("created_by"));
			airplane.setLastUpdated(rs.getDate("last_updated"));
			airplane.setLastUpdatedBy(rs.getString("last_updated_by"));
			return airplane;
		}
	}
}
