package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.EventDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface EventModelInt {
	public long add(EventDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(EventDTO dto)throws ApplicationException;
	public void update(EventDTO dto)throws ApplicationException,DuplicateRecordException;
	public EventDTO findByPk(long pk)throws ApplicationException;
	public EventDTO findByEvent(String eventName)throws ApplicationException;
	public EventDTO findByParticipent(String participentName)throws ApplicationException;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(EventDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public List search(EventDTO dto)throws ApplicationException;

}
