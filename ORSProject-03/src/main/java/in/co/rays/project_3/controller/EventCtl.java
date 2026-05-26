package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.EventDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.EventModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "EventCtl", urlPatterns = { "/ctl/EventCtl" })

public class EventCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(EventCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("participantName"))) {

			request.setAttribute("participantName", PropertyReader.getValue("error.require", "Participant Name"));

			pass = false;

		} else if (!DataValidator.isName(request.getParameter("participantName"))) {

			request.setAttribute("participantName", "Enter valid Participant Name");

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("eventName"))) {

			request.setAttribute("eventName", PropertyReader.getValue("error.require", "Event Name"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("email"))) {

			request.setAttribute("email", PropertyReader.getValue("error.require", "Email"));

			pass = false;

		} else if (!DataValidator.isEmail(request.getParameter("email"))) {

			request.setAttribute("email", "Enter valid Email");

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("registrationDate"))) {

			request.setAttribute("registrationDate", PropertyReader.getValue("error.require", "Registration Date"));

			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		EventDTO dto = new EventDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setParticipantName(DataUtility.getString(request.getParameter("participantName")));

		dto.setEventName(DataUtility.getString(request.getParameter("eventName")));

		dto.setEmail(DataUtility.getString(request.getParameter("email")));

		dto.setRegistrationDate(DataUtility.getDate(request.getParameter("registrationDate")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("EventCtl doGet Start");

		String op = DataUtility.getString(request.getParameter("operation"));

		EventModelInt model = (EventModelInt) ModelFactory.getInstance().getEventModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {

			EventDTO dto;

			try {

				dto = model.findByPk(id);

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {

				log.error(e);

				ServletUtility.handleException(e, request, response);

				return;
			}
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("EventCtl doGet End");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("EventCtl doPost Start");

		String op = DataUtility.getString(request.getParameter("operation"));

		EventModelInt model = (EventModelInt) ModelFactory.getInstance().getEventModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			EventDTO dto = (EventDTO) populateDTO(request);

			try {

				if (id > 0) {

					model.update(dto);

					ServletUtility.setSuccessMessage("Event Updated Successfully", request);

				} else {

					model.add(dto);

					ServletUtility.setSuccessMessage("Event Added Successfully", request);
				}

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {

				log.error(e);

				ServletUtility.handleException(e, request, response);

				return;

			} catch (DuplicateRecordException e) {

				ServletUtility.setErrorMessage("Event already exists", request);

				ServletUtility.setDto(dto, request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			EventDTO dto = (EventDTO) populateDTO(request);

			try {

				model.delete(dto);

				ServletUtility.redirect(ORSView.EVENT_LIST_CTL, request, response);

				return;

			} catch (ApplicationException e) {

				log.error(e);

				ServletUtility.handleException(e, request, response);

				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.EVENT_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.EVENT_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("EventCtl doPost End");
	}

	@Override
	protected String getView() {

		return ORSView.EVENT_VIEW;
	}
}