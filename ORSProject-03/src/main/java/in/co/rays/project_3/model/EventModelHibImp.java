package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.EventDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public class EventModelHibImp implements EventModelInt {

	@Override
	public long add(EventDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(EventDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(EventDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EventDTO findByPk(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventDTO findByEvent(String eventName) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventDTO findByParticipent(String participentName) throws ApplicationException {
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
