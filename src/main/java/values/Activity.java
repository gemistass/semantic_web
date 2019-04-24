package values;

import java.time.LocalDateTime;

public class Activity {

	private LocalDateTime start;
	private LocalDateTime end;
	private String content;
	private Observation[] observations;
	
	public Observation[] getObservations() {
		return observations;
	}

	public void setObservations(Observation[] observations) {
		this.observations = observations;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
