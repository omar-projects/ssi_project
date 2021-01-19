package fr.angers.univ.info.m2.acdi.reseau.client;

import fr.angers.univ.info.m2.acdi.reseau.models.ClePrivee;
import fr.angers.univ.info.m2.acdi.reseau.models.ClePublique;

// remote

public class Client {
	private ClePrivee m_clePrivee;
	private ClePublique m_clePublique;
	private ClePublique dist_client_pubKey;
	
	public Client() {
		this.m_clePublique = new ClePublique();
		this.m_clePrivee = new ClePrivee(this.m_clePublique);
                
                this.m_clePublique.setP(null);
		this.m_clePublique.setQ(null);
	}

	public ClePrivee getM_clePrivee() {
		return m_clePrivee;
	}

	public void setM_clePrivee(ClePrivee m_clePrivee) {
		this.m_clePrivee = m_clePrivee;
	}

	public ClePublique getM_clePublique() {
		return m_clePublique;
	}

	public void setM_clePublique(ClePublique m_clePublique) {
		this.m_clePublique = m_clePublique;
	}

	public ClePublique getDist_client_pubKey() {
		return dist_client_pubKey;
	}

	public void setDist_client_pubKey(ClePublique dist_client_pubKey) {
		this.dist_client_pubKey = dist_client_pubKey;
	}
}

