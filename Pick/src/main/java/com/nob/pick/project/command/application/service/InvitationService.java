package com.nob.pick.project.command.application.service;

import com.nob.pick.project.command.application.vo.RequestInviteEmailVO;

public interface InvitationService {
	void sendProjectInviteEmail(RequestInviteEmailVO requestInviteEmailVO);
}
