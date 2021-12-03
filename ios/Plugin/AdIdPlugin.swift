import Foundation
import Capacitor
import AdSupport
import AppTrackingTransparency

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(AdIdPlugin)
public class AdIdPlugin: CAPPlugin {

    
    @objc func getAdId(_ call: CAPPluginCall) {
        self.requestIDFAPermission(call)
    }
    
    
    var idfa: UUID {
        return ASIdentifierManager.shared().advertisingIdentifier
    }
    
    
    func requestIDFAPermission(_ call: CAPPluginCall) {
        if #available(iOS 14, *) {
            ATTrackingManager.requestTrackingAuthorization { (status) in
                switch status {
                    
                case .authorized:

                    // completion(self.idfa.uuidString)
                    call.resolve([
                        "id": self.idfa.uuidString
                    ])
                    
                case .denied, .notDetermined, .restricted:
                    
                    call.resolve([
                        "id": "none"
                    ])
                @unknown default:

                    call.resolve([
                        "id": "none"
                    ])
                }
            }
        } else {
            call.resolve([
                "id": self.idfa.uuidString
            ])
        }
    }
    
}
