library sdkwebvalidator.mock_endpoint_configuration;

var mockEndpointConfig = """
{
    "success": true,
    "message": null,
    "showMessageAsAlert": false,
    "name": "Work Order",
    "attributes": [
        {
            "name": "RequestID",
            "attributeType": "Text",
            "create": false,
            "createRequired": false,
            "update": false,
            "updateRequired": false,
            "search": false,
            "searchRequired": false,
            "attributeIndex": 0
        },
        {
            "name": "RequestNumber",
            "attributeType": "Text",
            "create": false,
            "createRequired": false,
            "update": false,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 1
        },
        {
            "name": "WorkOrderNumber",
            "attributeType": "Text",
            "create": false,
            "createRequired": false,
            "update": false,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 2
        },
        {
            "name": "ParentWorkOrderNumber",
            "attributeType": "Text",
            "create": false,
            "createRequired": false,
            "update": true,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 3
        },
        {
            "name": "AssignedTo",
            "attributeType": "ListItem",
            "create": true,
            "createRequired": false,
            "update": true,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 13,
            "relatedListServiceConfiguration": {
                "listName": "",
                "includesLocation": false,
                "authenticationRequired": false,
                "userIDIndex": -1,
                "canCache": false,
                "canSearch": false,
                "attributes": [
                    {
                        "attributeIndex": 3,
                        "label": "Email",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    },
                    {
                        "attributeIndex": 1,
                        "label": "AssignedToID",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    },
                    {
                        "attributeIndex": 0,
                        "label": "EmployeeID",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    },
                    {
                        "attributeIndex": 4,
                        "label": "Telephone",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    },
                    {
                        "attributeIndex": 5,
                        "label": "Crew",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    },
                    {
                        "attributeIndex": 2,
                        "label": "Name",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    }
                ],
                "serviceFilterParameters": []
            }
        },
        {
            "name": "ScheduleDate",
            "attributeType": "Date",
            "create": true,
            "createRequired": false,
            "update": true,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 23
        },
        {
            "name": "ScheduleUser",
            "attributeType": "Text",
            "create": true,
            "createRequired": false,
            "update": true,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 24
        },
        {
            "name": "Status",
            "attributeType": "ListItem",
            "create": true,
            "createRequired": true,
            "update": true,
            "updateRequired": true,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 28,
            "relatedListServiceConfiguration": {
                "listName": "",
                "includesLocation": false,
                "authenticationRequired": false,
                "userIDIndex": -1,
                "canCache": false,
                "canSearch": false,
                "attributes": [
                    {
                        "attributeIndex": 1,
                        "label": "Comments",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    },
                    {
                        "attributeIndex": 0,
                        "label": "WOStatusCode",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    }
                ],
                "serviceFilterParameters": []
            }
        },
        {
            "name": "Active",
            "attributeType": "Text",
            "create": false,
            "createRequired": false,
            "update": false,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 29
        },
        {
            "name": "CloseDate",
            "attributeType": "Date",
            "create": false,
            "createRequired": false,
            "update": true,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 30
        },
        {
            "name": "CloseUser",
            "attributeType": "Text",
            "create": false,
            "createRequired": false,
            "update": true,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 31
        },
        {
            "name": "CompleteDate",
            "attributeType": "Date",
            "create": false,
            "createRequired": false,
            "update": true,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 32
        },
        {
            "name": "Description",
            "attributeType": "Text",
            "create": true,
            "createRequired": true,
            "update": true,
            "updateRequired": true,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 4
        },
        {
            "name": "Requestor",
            "attributeType": "ListItem",
            "create": true,
            "createRequired": true,
            "update": true,
            "updateRequired": true,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 5,
            "relatedListServiceConfiguration": {
                "listName": "",
                "includesLocation": false,
                "authenticationRequired": false,
                "userIDIndex": -1,
                "canCache": false,
                "canSearch": false,
                "attributes": [
                    {
                        "attributeIndex": 0,
                        "label": "ID",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    },
                    {
                        "attributeIndex": 4,
                        "label": "Shift",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    },
                    {
                        "attributeIndex": 1,
                        "label": "Name",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    },
                    {
                        "attributeIndex": 3,
                        "label": "Telephone",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    },
                    {
                        "attributeIndex": 2,
                        "label": "Email",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    }
                ],
                "serviceFilterParameters": []
            }
        },
        {
            "name": "AlternateRequestor",
            "attributeType": "ListItem",
            "create": true,
            "createRequired": false,
            "update": true,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 6,
            "relatedListServiceConfiguration": {
                "listName": "",
                "includesLocation": false,
                "authenticationRequired": false,
                "userIDIndex": -1,
                "canCache": false,
                "canSearch": false,
                "attributes": [
                    {
                        "attributeIndex": 0,
                        "label": "ID",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    },
                    {
                        "attributeIndex": 1,
                        "label": "Name",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    },
                    {
                        "attributeIndex": 2,
                        "label": "Email",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    },
                    {
                        "attributeIndex": 3,
                        "label": "Telephone",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    },
                    {
                        "attributeIndex": 4,
                        "label": "Shift",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    }
                ],
                "serviceFilterParameters": []
            }
        },
        {
            "name": "Site",
            "attributeType": "ListItem",
            "create": true,
            "createRequired": true,
            "update": true,
            "updateRequired": true,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 7,
            "relatedListServiceConfiguration": {
                "listName": "",
                "includesLocation": false,
                "authenticationRequired": false,
                "userIDIndex": -1,
                "canCache": false,
                "canSearch": false,
                "attributes": [
                    {
                        "attributeIndex": 1,
                        "label": "DESCRIPTION",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    },
                    {
                        "attributeIndex": 0,
                        "label": "NAME",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    }
                ],
                "serviceFilterParameters": []
            }
        },
        {
            "name": "Building",
            "attributeType": "ListItem",
            "create": true,
            "createRequired": false,
            "update": true,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 8,
            "relatedListServiceConfiguration": {
                "listName": "",
                "includesLocation": false,
                "authenticationRequired": false,
                "userIDIndex": -1,
                "canCache": false,
                "canSearch": false,
                "attributes": [
                    {
                        "attributeIndex": 3,
                        "label": "SITE",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    },
                    {
                        "attributeIndex": 2,
                        "label": "ABBREVIATION",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    },
                    {
                        "attributeIndex": 1,
                        "label": "DESCRIPTION",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    },
                    {
                        "attributeIndex": 0,
                        "label": "NAME",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    }
                ],
                "serviceFilterParameters": []
            }
        },
        {
            "name": "Floor",
            "attributeType": "ListItem",
            "create": true,
            "createRequired": false,
            "update": true,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 9,
            "relatedListServiceConfiguration": {
                "listName": "",
                "includesLocation": false,
                "authenticationRequired": false,
                "userIDIndex": -1,
                "canCache": false,
                "canSearch": false,
                "attributes": [
                    {
                        "attributeIndex": 0,
                        "label": "NAME",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    },
                    {
                        "attributeIndex": 1,
                        "label": "DESCRIPTION",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    }
                ],
                "serviceFilterParameters": []
            }
        },
        {
            "name": "Room",
            "attributeType": "ListItem",
            "create": true,
            "createRequired": false,
            "update": true,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 10,
            "relatedListServiceConfiguration": {
                "listName": "",
                "includesLocation": false,
                "authenticationRequired": false,
                "userIDIndex": -1,
                "canCache": false,
                "canSearch": false,
                "attributes": [
                    {
                        "attributeIndex": 1,
                        "label": "DESCRIPTION",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    },
                    {
                        "attributeIndex": 0,
                        "label": "NAME",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    }
                ],
                "serviceFilterParameters": []
            }
        },
        {
            "name": "LocationAddress",
            "attributeType": "Text",
            "create": false,
            "createRequired": false,
            "update": false,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 12
        },
        {
            "name": "Craft",
            "attributeType": "ListItem",
            "create": true,
            "createRequired": false,
            "update": true,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 14,
            "relatedListServiceConfiguration": {
                "listName": "",
                "includesLocation": false,
                "authenticationRequired": false,
                "userIDIndex": -1,
                "canCache": false,
                "canSearch": false,
                "attributes": [
                    {
                        "attributeIndex": 1,
                        "label": "Comments",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    },
                    {
                        "attributeIndex": 0,
                        "label": "CraftCode",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    }
                ],
                "serviceFilterParameters": []
            }
        },
        {
            "name": "Crew",
            "attributeType": "ListItem",
            "create": true,
            "createRequired": false,
            "update": true,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 15,
            "relatedListServiceConfiguration": {
                "listName": "",
                "includesLocation": false,
                "authenticationRequired": false,
                "userIDIndex": -1,
                "canCache": false,
                "canSearch": false,
                "attributes": [
                    {
                        "attributeIndex": 1,
                        "label": "Comments",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    },
                    {
                        "attributeIndex": 0,
                        "label": "CrewCode",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    }
                ],
                "serviceFilterParameters": []
            }
        },
        {
            "name": "MaintenanceType",
            "attributeType": "ListItem",
            "create": true,
            "createRequired": true,
            "update": true,
            "updateRequired": true,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 16,
            "relatedListServiceConfiguration": {
                "listName": "",
                "includesLocation": false,
                "authenticationRequired": false,
                "userIDIndex": -1,
                "canCache": false,
                "canSearch": false,
                "attributes": [
                    {
                        "attributeIndex": 1,
                        "label": "Comments",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    },
                    {
                        "attributeIndex": 0,
                        "label": "MaintType",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    }
                ],
                "serviceFilterParameters": []
            }
        },
        {
            "name": "Method",
            "attributeType": "ListItem",
            "create": true,
            "createRequired": false,
            "update": true,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 17,
            "relatedListServiceConfiguration": {
                "listName": "",
                "includesLocation": false,
                "authenticationRequired": false,
                "userIDIndex": -1,
                "canCache": false,
                "canSearch": false,
                "attributes": [
                    {
                        "attributeIndex": 1,
                        "label": "Comments",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    },
                    {
                        "attributeIndex": 0,
                        "label": "MethodCode",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    }
                ],
                "serviceFilterParameters": []
            }
        },
        {
            "name": "OutageClass",
            "attributeType": "ListItem",
            "create": true,
            "createRequired": false,
            "update": true,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 18,
            "relatedListServiceConfiguration": {
                "listName": "",
                "includesLocation": false,
                "authenticationRequired": false,
                "userIDIndex": -1,
                "canCache": false,
                "canSearch": false,
                "attributes": [
                    {
                        "attributeIndex": 0,
                        "label": "OutageClassCode",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    },
                    {
                        "attributeIndex": 1,
                        "label": "Comments",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    }
                ],
                "serviceFilterParameters": []
            }
        },
        {
            "name": "Department",
            "attributeType": "ListItem",
            "create": true,
            "createRequired": false,
            "update": true,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 19,
            "relatedListServiceConfiguration": {
                "listName": "",
                "includesLocation": false,
                "authenticationRequired": false,
                "userIDIndex": -1,
                "canCache": false,
                "canSearch": false,
                "attributes": [
                    {
                        "attributeIndex": 1,
                        "label": "Description",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    },
                    {
                        "attributeIndex": 0,
                        "label": "DepartmentCode",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    }
                ],
                "serviceFilterParameters": []
            }
        },
        {
            "name": "Priority",
            "attributeType": "ListItem",
            "create": true,
            "createRequired": true,
            "update": true,
            "updateRequired": true,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 20,
            "relatedListServiceConfiguration": {
                "listName": "",
                "includesLocation": false,
                "authenticationRequired": false,
                "userIDIndex": -1,
                "canCache": false,
                "canSearch": false,
                "attributes": [
                    {
                        "attributeIndex": 0,
                        "label": "PriorityCode",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    },
                    {
                        "attributeIndex": 2,
                        "label": "Description",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    },
                    {
                        "attributeIndex": 1,
                        "label": "Comments",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    }
                ],
                "serviceFilterParameters": []
            }
        },
        {
            "name": "RequestType",
            "attributeType": "Text",
            "create": true,
            "createRequired": false,
            "update": true,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 21
        },
        {
            "name": "RequestDate",
            "attributeType": "Date",
            "create": true,
            "createRequired": true,
            "update": true,
            "updateRequired": true,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 22
        },
        {
            "name": "StartDate",
            "attributeType": "Date",
            "create": true,
            "createRequired": false,
            "update": true,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 25
        },
        {
            "name": "DueDate",
            "attributeType": "Date",
            "create": true,
            "createRequired": false,
            "update": true,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 26
        },
        {
            "name": "NonAvailableTime",
            "attributeType": "Text",
            "create": true,
            "createRequired": false,
            "update": true,
            "updateRequired": false,
            "search": false,
            "searchRequired": false,
            "attributeIndex": 27
        },
        {
            "name": "Tracking1",
            "attributeType": "Text",
            "create": true,
            "createRequired": false,
            "update": true,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 33
        },
        {
            "name": "Tracking2",
            "attributeType": "Text",
            "create": true,
            "createRequired": false,
            "update": true,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 34
        },
        {
            "name": "Equipment",
            "attributeType": "ListItem",
            "create": true,
            "createRequired": false,
            "update": true,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 35,
            "relatedListServiceConfiguration": {
                "listName": "",
                "includesLocation": false,
                "authenticationRequired": false,
                "userIDIndex": -1,
                "canCache": false,
                "canSearch": false,
                "attributes": [
                    {
                        "attributeIndex": 2,
                        "label": "EquipmentType",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    },
                    {
                        "attributeIndex": 3,
                        "label": "AssetClass",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    },
                    {
                        "attributeIndex": 1,
                        "label": "Nomenclature",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    },
                    {
                        "attributeIndex": 0,
                        "label": "EquipmentCode",
                        "attributeType": "Text",
                        "relatedListConfiguration": null
                    }
                ],
                "serviceFilterParameters": []
            }
        },
        {
            "name": "WarrantyDate",
            "attributeType": "Date",
            "create": false,
            "createRequired": false,
            "update": false,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 36
        },
        {
            "name": "WarrantyDescription",
            "attributeType": "Text",
            "create": false,
            "createRequired": false,
            "update": false,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 37
        },
        {
            "name": "PLNumber",
            "attributeType": "Text",
            "create": true,
            "createRequired": false,
            "update": true,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 38
        },
        {
            "name": "CPNumber",
            "attributeType": "Text",
            "create": true,
            "createRequired": false,
            "update": true,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 39
        },
        {
            "name": "LocationDescription",
            "attributeType": "Text",
            "create": false,
            "createRequired": false,
            "update": false,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 40
        },
        {
            "name": "Coordinates",
            "attributeType": "Text",
            "create": true,
            "createRequired": false,
            "update": true,
            "updateRequired": false,
            "search": false,
            "searchRequired": false,
            "attributeIndex": 41
        },
        {
            "name": "TaskList",
            "attributeType": "Text",
            "create": true,
            "createRequired": false,
            "update": true,
            "updateRequired": false,
            "search": false,
            "searchRequired": false,
            "attributeIndex": 42
        },
        {
            "name": "EnterDate",
            "attributeType": "Date",
            "create": false,
            "createRequired": false,
            "update": false,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 43
        },
        {
            "name": "EnterUser",
            "attributeType": "Text",
            "create": false,
            "createRequired": false,
            "update": false,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 44
        },
        {
            "name": "ModifyDate",
            "attributeType": "Date",
            "create": false,
            "createRequired": false,
            "update": false,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 45
        },
        {
            "name": "ModifyUser",
            "attributeType": "Text",
            "create": false,
            "createRequired": false,
            "update": false,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 46
        },
        {
            "name": "Complaint Code",
            "attributeType": "ListItem",
            "create": true,
            "createRequired": false,
            "update": true,
            "updateRequired": false,
            "search": true,
            "searchRequired": false,
            "attributeIndex": 60
        },
        {
            "name": "Attachments",
            "attributeType": "Attachments",
            "create": true,
            "createRequired": false,
            "update": true,
            "updateRequired": false,
            "search": false,
            "searchRequired": false,
            "attributeIndex": 77
        },
        {
            "name": "ChildWorkOrders",
            "relatedService": {
                "success": true,
                "message": null,
                "showMessageAsAlert": false,
                "name": "ChildWorkOrders",
                "attributes": [
                    {
                        "name": "RequestID",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 0
                    },
                    {
                        "name": "RequestNumber",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 1
                    },
                    {
                        "name": "WorkOrderNumber",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 2
                    },
                    {
                        "name": "ParentWorkOrderNumber",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 3
                    },
                    {
                        "name": "AssignedTo",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 13,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 1,
                                    "label": "AssignedToID",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 4,
                                    "label": "Telephone",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 5,
                                    "label": "Crew",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 2,
                                    "label": "Name",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 0,
                                    "label": "EmployeeID",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 3,
                                    "label": "Email",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    {
                        "name": "ScheduleDate",
                        "attributeType": "Date",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 23
                    },
                    {
                        "name": "ScheduleUser",
                        "attributeType": "Text",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 24
                    },
                    {
                        "name": "Status",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": true,
                        "update": true,
                        "updateRequired": true,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 28,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 1,
                                    "label": "Comments",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 0,
                                    "label": "WOStatusCode",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    {
                        "name": "Active",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 29
                    },
                    {
                        "name": "CloseDate",
                        "attributeType": "Date",
                        "create": false,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 30
                    },
                    {
                        "name": "CloseUser",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 31
                    },
                    {
                        "name": "CompleteDate",
                        "attributeType": "Date",
                        "create": false,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 32
                    },
                    {
                        "name": "Description",
                        "attributeType": "Text",
                        "create": true,
                        "createRequired": true,
                        "update": true,
                        "updateRequired": true,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 4
                    },
                    {
                        "name": "Requestor",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": true,
                        "update": true,
                        "updateRequired": true,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 5,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 0,
                                    "label": "ID",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 1,
                                    "label": "Name",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 4,
                                    "label": "Shift",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 2,
                                    "label": "Email",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 3,
                                    "label": "Telephone",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    {
                        "name": "AlternateRequestor",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 6,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 4,
                                    "label": "Shift",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 3,
                                    "label": "Telephone",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 0,
                                    "label": "ID",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 2,
                                    "label": "Email",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 1,
                                    "label": "Name",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    {
                        "name": "Site",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": true,
                        "update": true,
                        "updateRequired": true,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 7,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 0,
                                    "label": "NAME",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 1,
                                    "label": "DESCRIPTION",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    {
                        "name": "Building",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 8,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 2,
                                    "label": "ABBREVIATION",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 1,
                                    "label": "DESCRIPTION",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 0,
                                    "label": "NAME",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 3,
                                    "label": "SITE",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    {
                        "name": "Floor",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 9,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 1,
                                    "label": "DESCRIPTION",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 0,
                                    "label": "NAME",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    {
                        "name": "Room",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 10,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 1,
                                    "label": "DESCRIPTION",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 0,
                                    "label": "NAME",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    {
                        "name": "LocationAddress",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 12
                    },
                    {
                        "name": "Craft",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 14,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 1,
                                    "label": "Comments",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 0,
                                    "label": "CraftCode",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    {
                        "name": "Crew",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 15,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 1,
                                    "label": "Comments",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 0,
                                    "label": "CrewCode",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    {
                        "name": "MaintenanceType",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": true,
                        "update": true,
                        "updateRequired": true,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 16,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 0,
                                    "label": "MaintType",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 1,
                                    "label": "Comments",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    {
                        "name": "Method",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 17,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 0,
                                    "label": "MethodCode",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 1,
                                    "label": "Comments",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    {
                        "name": "OutageClass",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 18,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 0,
                                    "label": "OutageClassCode",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 1,
                                    "label": "Comments",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    {
                        "name": "Department",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 19,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 0,
                                    "label": "DepartmentCode",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 1,
                                    "label": "Description",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    {
                        "name": "Priority",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": true,
                        "update": true,
                        "updateRequired": true,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 20,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 0,
                                    "label": "PriorityCode",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 2,
                                    "label": "Description",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 1,
                                    "label": "Comments",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    {
                        "name": "RequestType",
                        "attributeType": "Text",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 21
                    },
                    {
                        "name": "RequestDate",
                        "attributeType": "Date",
                        "create": true,
                        "createRequired": true,
                        "update": true,
                        "updateRequired": true,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 22
                    },
                    {
                        "name": "StartDate",
                        "attributeType": "Date",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 25
                    },
                    {
                        "name": "DueDate",
                        "attributeType": "Date",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 26
                    },
                    {
                        "name": "NonAvailableTime",
                        "attributeType": "Text",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 27
                    },
                    {
                        "name": "Tracking1",
                        "attributeType": "Text",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 33
                    },
                    {
                        "name": "Tracking2",
                        "attributeType": "Text",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 34
                    },
                    {
                        "name": "Equipment",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 35,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 1,
                                    "label": "Nomenclature",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 2,
                                    "label": "EquipmentType",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 0,
                                    "label": "EquipmentCode",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 3,
                                    "label": "AssetClass",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    {
                        "name": "WarrantyDate",
                        "attributeType": "Date",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 36
                    },
                    {
                        "name": "WarrantyDescription",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 37
                    },
                    {
                        "name": "PLNumber",
                        "attributeType": "Text",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 38
                    },
                    {
                        "name": "CPNumber",
                        "attributeType": "Text",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 39
                    },
                    {
                        "name": "LocationDescription",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 40
                    },
                    {
                        "name": "Coordinates",
                        "attributeType": "Text",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 41
                    },
                    {
                        "name": "TaskList",
                        "attributeType": "Text",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 42
                    },
                    {
                        "name": "EnterDate",
                        "attributeType": "Date",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 43
                    },
                    {
                        "name": "EnterUser",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 44
                    },
                    {
                        "name": "ModifyDate",
                        "attributeType": "Date",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 45
                    },
                    {
                        "name": "ModifyUser",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 46
                    },
                    {
                        "name": "Complaint Code",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 60
                    },
                    {
                        "name": "Attachments",
                        "attributeType": "Attachments",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 77
                    }
                ],
                "serviceFilterParameters": null,
                "attributeConfigurationForIndexMap": {
                    "0": {
                        "name": "RequestID",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 0
                    },
                    "1": {
                        "name": "RequestNumber",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 1
                    },
                    "2": {
                        "name": "WorkOrderNumber",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 2
                    },
                    "3": {
                        "name": "ParentWorkOrderNumber",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 3
                    },
                    "4": {
                        "name": "Description",
                        "attributeType": "Text",
                        "create": true,
                        "createRequired": true,
                        "update": true,
                        "updateRequired": true,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 4
                    },
                    "5": {
                        "name": "Requestor",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": true,
                        "update": true,
                        "updateRequired": true,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 5,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 0,
                                    "label": "ID",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 1,
                                    "label": "Name",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 4,
                                    "label": "Shift",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 2,
                                    "label": "Email",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 3,
                                    "label": "Telephone",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    "6": {
                        "name": "AlternateRequestor",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 6,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 4,
                                    "label": "Shift",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 3,
                                    "label": "Telephone",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 0,
                                    "label": "ID",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 2,
                                    "label": "Email",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 1,
                                    "label": "Name",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    "7": {
                        "name": "Site",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": true,
                        "update": true,
                        "updateRequired": true,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 7,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 0,
                                    "label": "NAME",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 1,
                                    "label": "DESCRIPTION",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    "8": {
                        "name": "Building",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 8,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 2,
                                    "label": "ABBREVIATION",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 1,
                                    "label": "DESCRIPTION",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 0,
                                    "label": "NAME",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 3,
                                    "label": "SITE",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    "9": {
                        "name": "Floor",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 9,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 1,
                                    "label": "DESCRIPTION",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 0,
                                    "label": "NAME",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    "10": {
                        "name": "Room",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 10,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 1,
                                    "label": "DESCRIPTION",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 0,
                                    "label": "NAME",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    "12": {
                        "name": "LocationAddress",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 12
                    },
                    "13": {
                        "name": "AssignedTo",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 13,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 1,
                                    "label": "AssignedToID",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 4,
                                    "label": "Telephone",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 5,
                                    "label": "Crew",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 2,
                                    "label": "Name",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 0,
                                    "label": "EmployeeID",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 3,
                                    "label": "Email",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    "77": {
                        "name": "Attachments",
                        "attributeType": "Attachments",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 77
                    },
                    "14": {
                        "name": "Craft",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 14,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 1,
                                    "label": "Comments",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 0,
                                    "label": "CraftCode",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    "15": {
                        "name": "Crew",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 15,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 1,
                                    "label": "Comments",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 0,
                                    "label": "CrewCode",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    "16": {
                        "name": "MaintenanceType",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": true,
                        "update": true,
                        "updateRequired": true,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 16,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 0,
                                    "label": "MaintType",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 1,
                                    "label": "Comments",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    "17": {
                        "name": "Method",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 17,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 0,
                                    "label": "MethodCode",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 1,
                                    "label": "Comments",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    "18": {
                        "name": "OutageClass",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 18,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 0,
                                    "label": "OutageClassCode",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 1,
                                    "label": "Comments",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    "19": {
                        "name": "Department",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 19,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 0,
                                    "label": "DepartmentCode",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 1,
                                    "label": "Description",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    "20": {
                        "name": "Priority",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": true,
                        "update": true,
                        "updateRequired": true,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 20,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 0,
                                    "label": "PriorityCode",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 2,
                                    "label": "Description",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 1,
                                    "label": "Comments",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    "21": {
                        "name": "RequestType",
                        "attributeType": "Text",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 21
                    },
                    "22": {
                        "name": "RequestDate",
                        "attributeType": "Date",
                        "create": true,
                        "createRequired": true,
                        "update": true,
                        "updateRequired": true,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 22
                    },
                    "23": {
                        "name": "ScheduleDate",
                        "attributeType": "Date",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 23
                    },
                    "24": {
                        "name": "ScheduleUser",
                        "attributeType": "Text",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 24
                    },
                    "25": {
                        "name": "StartDate",
                        "attributeType": "Date",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 25
                    },
                    "26": {
                        "name": "DueDate",
                        "attributeType": "Date",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 26
                    },
                    "27": {
                        "name": "NonAvailableTime",
                        "attributeType": "Text",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 27
                    },
                    "28": {
                        "name": "Status",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": true,
                        "update": true,
                        "updateRequired": true,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 28,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 1,
                                    "label": "Comments",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 0,
                                    "label": "WOStatusCode",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    "29": {
                        "name": "Active",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 29
                    },
                    "30": {
                        "name": "CloseDate",
                        "attributeType": "Date",
                        "create": false,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 30
                    },
                    "31": {
                        "name": "CloseUser",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 31
                    },
                    "32": {
                        "name": "CompleteDate",
                        "attributeType": "Date",
                        "create": false,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 32
                    },
                    "33": {
                        "name": "Tracking1",
                        "attributeType": "Text",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 33
                    },
                    "34": {
                        "name": "Tracking2",
                        "attributeType": "Text",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 34
                    },
                    "35": {
                        "name": "Equipment",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 35,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 1,
                                    "label": "Nomenclature",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 2,
                                    "label": "EquipmentType",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 0,
                                    "label": "EquipmentCode",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 3,
                                    "label": "AssetClass",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    "36": {
                        "name": "WarrantyDate",
                        "attributeType": "Date",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 36
                    },
                    "37": {
                        "name": "WarrantyDescription",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 37
                    },
                    "38": {
                        "name": "PLNumber",
                        "attributeType": "Text",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 38
                    },
                    "39": {
                        "name": "CPNumber",
                        "attributeType": "Text",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 39
                    },
                    "40": {
                        "name": "LocationDescription",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 40
                    },
                    "41": {
                        "name": "Coordinates",
                        "attributeType": "Text",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 41
                    },
                    "42": {
                        "name": "TaskList",
                        "attributeType": "Text",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 42
                    },
                    "43": {
                        "name": "EnterDate",
                        "attributeType": "Date",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 43
                    },
                    "44": {
                        "name": "EnterUser",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 44
                    },
                    "45": {
                        "name": "ModifyDate",
                        "attributeType": "Date",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 45
                    },
                    "46": {
                        "name": "ModifyUser",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 46
                    },
                    "60": {
                        "name": "Complaint Code",
                        "attributeType": "ListItem",
                        "create": true,
                        "createRequired": false,
                        "update": true,
                        "updateRequired": false,
                        "search": true,
                        "searchRequired": false,
                        "attributeIndex": 60
                    }
                },
                "dependentLists": null
            },
            "attributeType": "Relationship",
            "create": false,
            "createRequired": false,
            "update": false,
            "updateRequired": false,
            "search": false,
            "searchRequired": false,
            "attributeIndex": 78
        },
        {
            "name": "Accounts",
            "relatedService": {
                "success": true,
                "message": null,
                "showMessageAsAlert": false,
                "name": "Accounts",
                "attributes": [
                    {
                        "name": "AccountID",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 0
                    },
                    {
                        "name": "RequestID",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 1
                    },
                    {
                        "name": "BillingTypeID",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 2
                    },
                    {
                        "name": "BillingType",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 3
                    },
                    {
                        "name": "AccountHold",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 4
                    },
                    {
                        "name": "Reason",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 5
                    },
                    {
                        "name": "ChartOfAccounts",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 6
                    },
                    {
                        "name": "AccountSegment1",
                        "attributeType": "ListItem",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 7,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 0,
                                    "label": "Account Segment 1",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 1,
                                    "label": "Description",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    {
                        "name": "AccountSegment2",
                        "attributeType": "ListItem",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 8,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 0,
                                    "label": "Account Segment 2",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 1,
                                    "label": "Description",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    {
                        "name": "AccountSegment3",
                        "attributeType": "ListItem",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 9,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 1,
                                    "label": "Description",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 0,
                                    "label": "Account Segment 3",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    {
                        "name": "AccountSegment4",
                        "attributeType": "ListItem",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 10,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 1,
                                    "label": "Description",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 0,
                                    "label": "Account Segment 4",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    {
                        "name": "AccountSegment5",
                        "attributeType": "ListItem",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 11,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 0,
                                    "label": "Account Segment 5",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 1,
                                    "label": "Description",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    {
                        "name": "AccountSegment6",
                        "attributeType": "ListItem",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 12,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 0,
                                    "label": "Account Segment 6",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 1,
                                    "label": "Description",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    {
                        "name": "Percent",
                        "attributeType": "Integer",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 13
                    },
                    {
                        "name": "Attribute1",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 14
                    },
                    {
                        "name": "Attribute2",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 15
                    },
                    {
                        "name": "Attribute3",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 16
                    },
                    {
                        "name": "AccountString",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 17
                    }
                ],
                "serviceFilterParameters": null,
                "attributeConfigurationForIndexMap": {
                    "0": {
                        "name": "AccountID",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 0
                    },
                    "1": {
                        "name": "RequestID",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 1
                    },
                    "2": {
                        "name": "BillingTypeID",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 2
                    },
                    "3": {
                        "name": "BillingType",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 3
                    },
                    "4": {
                        "name": "AccountHold",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 4
                    },
                    "5": {
                        "name": "Reason",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 5
                    },
                    "6": {
                        "name": "ChartOfAccounts",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 6
                    },
                    "7": {
                        "name": "AccountSegment1",
                        "attributeType": "ListItem",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 7,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 0,
                                    "label": "Account Segment 1",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 1,
                                    "label": "Description",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    "8": {
                        "name": "AccountSegment2",
                        "attributeType": "ListItem",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 8,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 0,
                                    "label": "Account Segment 2",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 1,
                                    "label": "Description",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    "9": {
                        "name": "AccountSegment3",
                        "attributeType": "ListItem",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 9,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 1,
                                    "label": "Description",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 0,
                                    "label": "Account Segment 3",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    "10": {
                        "name": "AccountSegment4",
                        "attributeType": "ListItem",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 10,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 1,
                                    "label": "Description",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 0,
                                    "label": "Account Segment 4",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    "11": {
                        "name": "AccountSegment5",
                        "attributeType": "ListItem",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 11,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 0,
                                    "label": "Account Segment 5",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 1,
                                    "label": "Description",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    "12": {
                        "name": "AccountSegment6",
                        "attributeType": "ListItem",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 12,
                        "relatedListServiceConfiguration": {
                            "listName": "",
                            "includesLocation": false,
                            "authenticationRequired": false,
                            "userIDIndex": -1,
                            "canCache": false,
                            "canSearch": false,
                            "attributes": [
                                {
                                    "attributeIndex": 0,
                                    "label": "Account Segment 6",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                },
                                {
                                    "attributeIndex": 1,
                                    "label": "Description",
                                    "attributeType": "Text",
                                    "relatedListConfiguration": null
                                }
                            ],
                            "serviceFilterParameters": []
                        }
                    },
                    "13": {
                        "name": "Percent",
                        "attributeType": "Integer",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 13
                    },
                    "14": {
                        "name": "Attribute1",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 14
                    },
                    "15": {
                        "name": "Attribute2",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 15
                    },
                    "16": {
                        "name": "Attribute3",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 16
                    },
                    "17": {
                        "name": "AccountString",
                        "attributeType": "Text",
                        "create": false,
                        "createRequired": false,
                        "update": false,
                        "updateRequired": false,
                        "search": false,
                        "searchRequired": false,
                        "attributeIndex": 17
                    }
                },
                "dependentLists": null
            },
            "attributeType": "Relationship",
            "create": true,
            "createRequired": false,
            "update": true,
            "updateRequired": false,
            "search": false,
            "searchRequired": false,
            "attributeIndex": 79
        }
    ],
    "serviceFilterParameters": [
        {
            "key": "MINE",
            "type": "Boolean",
            "possibleValues": null,
            "required": false
        },
        {
            "key": "CREW",
            "type": "Boolean",
            "possibleValues": null,
            "required": false
        },
        {
            "key": "PM",
            "type": "Boolean",
            "possibleValues": null,
            "required": false
        },
        {
            "key": "PMCREW",
            "type": "Boolean",
            "possibleValues": null,
            "required": false
        },
        {
            "key": "PMROUTE",
            "type": "Boolean",
            "possibleValues": null,
            "required": false
        },
        {
            "key": "PMCOMPLETE",
            "type": "Boolean",
            "possibleValues": null,
            "required": false
        },
        {
            "key": "PMUNASSIGNED",
            "type": "Boolean",
            "possibleValues": null,
            "required": false
        },
        {
            "key": "SWO",
            "type": "Boolean",
            "possibleValues": null,
            "required": false
        },
        {
            "key": "SWOCREW",
            "type": "Boolean",
            "possibleValues": null,
            "required": false
        },
        {
            "key": "PROJECT",
            "type": "Boolean",
            "possibleValues": null,
            "required": false
        },
        {
            "key": "UNASSIGNED",
            "type": "Boolean",
            "possibleValues": null,
            "required": false
        },
        {
            "key": "PENDINGCOMPLETE",
            "type": "Boolean",
            "possibleValues": null,
            "required": false
        },
        {
            "key": "PENDINGCOMPLETEWO",
            "type": "Boolean",
            "possibleValues": null,
            "required": false
        },
        {
            "key": "MATERIALISSUE",
            "type": "Boolean",
            "possibleValues": null,
            "required": false
        },
        {
            "key": "CREWSUPERVISOR",
            "type": "Boolean",
            "possibleValues": null,
            "required": false
        },
        {
            "key": "REVIEW",
            "type": "Boolean",
            "possibleValues": null,
            "required": false
        }
    ],
    "dependentLists": null
}
""";