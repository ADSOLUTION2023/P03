package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import in.co.rays.project_3.dto.EventDTO;
import in.co.rays.project_3.dto.UserDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

public class EventModelJDBC implements EventModelInt {

	public long nextPK() throws DatabaseException {
		Connection con = null;
		long pk = 0;
		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select max(id) from ST_EVENT");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getLong(1);
				System.out.println(pk);
			}
		} catch (Exception e) {
			throw new DatabaseException("Database Exception" + e);

		} finally {
			JDBCDataSource.closeConnection(con);
		}
		return pk + 1;

	}

	@Override
	public long add(EventDTO dto) throws ApplicationException, DuplicateRecordException {
		Connection con = null;
		long pk = 0;
		EventDTO existDto = null;
		existDto = findByEventName(dto.getEventName());
		EventDTO existDto1 = findByParticipant(dto.getParticipantName());
		if (existDto != null && existDto1 != null) {
			throw new DuplicateRecordException("Participant already Registered for the event");
		}
		try {
			con = JDBCDataS
					
					
					\\\\\\\\\\\\\\\\\\\\\\\\\\\
			con.setAutoCommit(false);
			pk = nextPK();
			PreparedStatement ps = con.prepareStatement("insert into ST_EVENT values(?,?,?,?,?)");
			ps.setLong(1, pk);
			ps.setString(2, dto.getParticipantName());
			ps.setString(3, dto.getEventName());
			ps.setString(4, dto.getEmail());
			ps.setDate(5, (Date) dto.getRegistrationDate());
			/*
			 * ps.setString(15, dto.getCreatedBy()); ps.setString(16, dto.getModifiedBy());
			 * ps.setTimestamp(17, dto.getCreatedDatetime()); ps.setTimestamp(18,
			 * dto.getModifiedDatetime());
			 */
			ps.executeUpdate();
			con.commit();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();

			} catch (Exception e2) {
				e2.printStackTrace();
				e.printStackTrace();
				throw new ApplicationException("exception: add rollback exception:" + e2.getMessage());

			}
			throw new ApplicationException("Exception : Exception in add Event");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		return pk;
	}

	@Override
	public void update(EventDTO dto) throws ApplicationException, DuplicateRecordException {
		Connection con = null;
		long pk = 0;
		EventDTO existDto = null;
		existDto = findByEventName(dto.getEventName());
		EventDTO existDto1 = findByParticipant(dto.getParticipantName());
		if (existDto != null && existDto1 != null) {
			throw new DuplicateRecordException("Participant already Registered for the event");
		}
		try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			pk = nextPK();
			PreparedStatement ps = con
					.prepareStatement("update ST_EVENT set PARTICIPANT_NAME=?,EVENT_NAME=?,EMAIL=?,REGISTRATION_DATE=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			ps.setString(2, dto.getParticipantName());
			ps.setString(3, dto.getEventName());
			ps.setString(4, dto.getEmail());
			ps.setDate(5, (Date) dto.getRegistrationDate());
			ps.setString(15, dto.getCreatedBy());
			ps.setString(16, dto.getModifiedBy());
			ps.setTimestamp(17, dto.getCreatedDatetime());
			ps.setTimestamp(18, dto.getModifiedDatetime());

			ps.executeUpdate();
			con.commit();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();

			} catch (Exception e2) {
				e2.printStackTrace();
				e.printStackTrace();
				throw new ApplicationException("exception: add rollback exception:" + e2.getMessage());

			}
			throw new ApplicationException("Exception : Exception in updating Event");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

	}

	@Override
	public void delete(EventDTO dto) throws ApplicationException {
		Connection con = null;
		try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement("delete from ST_EVENTe id=?");
			ps.setLong(1, dto.getId());
			ps.executeUpdate();
			con.commit();
			ps.close();

		} catch (Exception e) {
			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete User");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

	}
,@Override
	public EventDTO findByPK(long pk) throws ApplicationException {
		Connection con = null;
		PreparedStatement ps = null;
		UserDTO dto = null;
		try {
			con = JDBCDataSource.getConnection();
			ps = con.prepareStatement("select * from ST_USER where id=?");
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new EventDTO
		return null;
	}

	@Override
	public EventDTO findByLogin(String login) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List search(EventDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List search(EventDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

}
